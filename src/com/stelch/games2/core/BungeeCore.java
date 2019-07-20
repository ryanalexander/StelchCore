/*
 *
 * *
 *  *
 *  * © Stelch Games 2019, distribution is strictly prohibited
 *  *
 *  * Changes to this file must be documented on push.
 *  * Unauthorised changes to this file are prohibited.
 *  *
 *  * @author Ryan Wood
 *  * @since 14/7/2019
 *
 */

package com.stelch.games2.core;

import com.stelch.games2.core.Commands.bungee.hub;
import com.stelch.games2.core.Commands.bungee.servers;
import com.stelch.games2.core.Events.bungee.*;
import com.stelch.games2.core.Utils.JedisUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BungeeCore extends Plugin {

    public static JedisPool pool;

    public static HashMap<String, GameServer> servers=new HashMap<>();
    public static HashMap<String, HashMap<Integer,String>> category_servers = new HashMap<>();

    @Override
    public void onEnable() {
         /*
         * Define Plugin Manager
         */
        PluginManager pm = getProxy().getPluginManager();

        /*
         * Register commands
         */
        pm.registerCommand(this,new servers());
        pm.registerCommand(this,new hub());

        /*
         * Register BungeeCord Events
         */

        getProxy().getPluginManager().registerListener(this,new playerChangeServer());
        getProxy().getPluginManager().registerListener(this,new playerJoin());
        getProxy().getPluginManager().registerListener(this,new playerKick());
        getProxy().getPluginManager().registerListener(this,new playerLeave());
        getProxy().getPluginManager().registerListener(this,new proxyPing());

        /**
         * Connect Jedis client to Redis server
         */
        JedisUtils.init(pool,this);

        /**
         * Start BungeeServer handler
         */
        servers();
    }

    private void servers() {
        getProxy().getScheduler().schedule(this, new Runnable() {
            @Override
            public void run() {
                ArrayList<String> new_uuid = new ArrayList<>();
                for(Map.Entry<String,GameServer> serverPayload : getServers().entrySet()){
                    new_uuid.add(serverPayload.getValue().uuid);
                    GameServer server = serverPayload.getValue();
                    if(server.ip==null)continue;
                    if(servers.containsKey(server.uuid)){
                        if((Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis()-server.lastPoll)>6000||server.lastPoll==0){
                            try(Jedis jedis = pool.getResource()){
                                System.out.println(String.format("REMOVED DEAD SERVER [NAME %s]",server.name));
                                jedis.del(String.format("SERVER|%s|name",server.uuid));
                                jedis.del(String.format("SERVER|%s|ipport",server.uuid));
                                jedis.del(String.format("SERVER|%s|playercount",server.uuid));
                                jedis.del(String.format("SERVER|%s|game",server.uuid));
                                jedis.del(String.format("SERVER|%s|state",server.uuid));
                                jedis.del(String.format("SERVER|%s|last_poll",server.uuid));
                                category_servers.get(server.type).remove(server.id);
                                ProxyServer.getInstance().getServers().remove(server.getName());
                                servers.remove(server.uuid);
                            }
                        }
                        continue;
                    }

                    int server_id = getLowestIdOfGroup(server.getType());
                    if(!(category_servers.get(server.getType()).containsValue(server.uuid))){
                        category_servers.get(server.getType()).put(server_id,server.uuid);
                    }
                    String name = server.getType()+server_id;
                    System.out.println(String.format("ADDED NEW SERVER [NAME %s]",name));
                    try(Jedis jedis = pool.getResource()){
                        jedis.set(String.format("SERVER|%s|name",server.uuid),name);
                    }
                    server.setId(server_id);
                    server.setName(name);
                    ProxyServer.getInstance().getServers().put(name,server.build());
                    servers.put(server.uuid,server);
                }
                for(GameServer server : servers.values()){
                    System.out.println(new_uuid);
                    if(!(new_uuid.contains(server.uuid))){
                        System.out.println(String.format("REMOVED DEAD SERVER [NAME %s]",server.name));
                        category_servers.get(server.type).remove(server.id);
                        ProxyServer.getInstance().getServers().remove(server.getName());
                        servers.remove(server.uuid);
                    }
                }

            }
        },1,2, TimeUnit.SECONDS);
    }

    /**
     *
     * @return UUID, GameServer object
     */
    private HashMap<String, GameServer> getServers() {
        HashMap<String, GameServer> gameServers = new HashMap<>();

        try (Jedis jedis = pool.getResource()){
            Set<String> servers = jedis.keys("SERVER|*");
            Iterator<String> iterator = servers.iterator();
            while(iterator.hasNext()){
                String argument = iterator.next();
                String[] args = argument.split("[|]");
                String uuid = args[1];
                String field = args[2];
                String data = jedis.get(argument);
                GameServer server;
                if(gameServers.containsKey(uuid)){server=gameServers.get(uuid);}else{server=new GameServer(uuid);gameServers.put(uuid,server);}

                switch(field.toUpperCase()){
                    case "NAME":
                        server.setName(data.toUpperCase());
                        break;
                    case "IPPORT":
                        String[] i = data.split("[:]");
                        server.setIp(i[0]);
                        server.setPort(Integer.parseInt(i[1]));
                        break;
                    case "GAME":
                        server.setGame(data);
                        break;
                    case "TYPE":
                        addCategoryIfDoesntExist(data.toUpperCase());
                        server.setType(data.toUpperCase());
                        break;
                    case "PLAYERCOUNT":
                        server.setPlayercount(Integer.valueOf(data));
                        break;
                    case "LAST_POLL":
                        server.setLastPoll(data);
                        break;
                }
                gameServers.put(uuid,server);
            }
        }
        return gameServers;
    }

    private int getLowestIdOfGroup(String group){
        if(category_servers.containsKey(group.toUpperCase())){
            ArrayList<Integer> server_ids = new ArrayList<>();
            for(Map.Entry<Integer, String> payload : category_servers.get(group.toUpperCase()).entrySet()) server_ids.add(payload.getKey());
            for(int i=1;i<99;i++)
                if(!(server_ids.contains(i)))return i;
            return 0;
        }else
            return 0;
    }

    private void addCategoryIfDoesntExist(String catName){
        if(!(category_servers.containsKey(catName))){
            category_servers.put(catName,new HashMap<>());
        }
    }

    public class GameServer {
        private String uuid;
        private String name = null;
        private String state;
        private String type;
        private String game;
        private String ip;
        private long lastPoll=0;
        private ServerInfo serverInfo=null;
        private int id;
        private int port;
        private int playercount;

        public GameServer(String uuid) {
            this.uuid = uuid;
        }

        public void setLastPoll(String pollTime){
            this.lastPoll=Long.valueOf(pollTime);
        }

        public void setGame(String game) {
            this.game = game;
        }

        public void setType(String type) {
            if (!category_servers.containsKey(type.toUpperCase())) {
                category_servers.put(type.toUpperCase(), new HashMap<>());
            }
            this.type = type;
        }

        public String getType() {
            return type != null ? type : "undefined";
        }

        public void setPlayercount(int playercount) {
            this.playercount = playercount;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUuid() {
            return uuid;
        }

        public String getName() {
            return name;
        }

        public String getIp() {
            return ip;
        }

        public long getLastPoll() {
            return lastPoll;
        }

        public String getState() {
            return state;
        }

        public String getGame() {
            return game;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ServerInfo getServerInfo() {
            return serverInfo;
        }

        public ServerInfo build() {
            if (this.ip == null) {
                System.out.println("FAILED BUILDING BUNGEE SERVER [NoIP]");
                return null;
            }
            this.serverInfo=((this.serverInfo==null)?ProxyServer.getInstance().constructServerInfo(this.name, InetSocketAddress.createUnresolved(this.ip, this.port), this.game, false):this.serverInfo);
            return this.serverInfo;
        }
    }

}
