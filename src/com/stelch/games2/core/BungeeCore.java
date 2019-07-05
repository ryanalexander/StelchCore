package com.stelch.games2.core;

import com.stelch.games2.core.Commands.bungee.servers;
import com.stelch.games2.core.Events.bungee.playerChangeServer;
import com.stelch.games2.core.Events.bungee.playerJoin;
import com.stelch.games2.core.Events.bungee.playerLeave;
import com.stelch.games2.core.Events.bungee.proxyPing;
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

    private static HashMap<String, ServerInfo> servers=new HashMap<>();

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

        /*
         * Register BungeeCord Events
         */

        getProxy().getPluginManager().registerListener(this,new playerChangeServer());
        getProxy().getPluginManager().registerListener(this,new playerJoin());
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
                try (Jedis jedis = BungeeCore.pool.getResource()){
                    Set<String> names = jedis.keys("SERVER|*");
                    List<String> server_uuids = new ArrayList<>(servers.keySet());
                    HashMap<String,GameServer> new_servers = new HashMap<>();

                    java.util.Iterator<String> it = names.iterator();
                    while(it.hasNext()) {
                        String s = it.next();
                        String[] args = s.split("[|]");
                        String uuid = args[1];
                        String datafield = args[2];
                        String data = jedis.get(s);
                        if(server_uuids.contains(uuid)){server_uuids.remove(uuid);}
                        if(servers.containsKey(uuid)){continue;}
                        GameServer server = new_servers.containsKey(uuid)?new_servers.get(uuid):new GameServer(uuid);
                        switch(datafield.toLowerCase()){
                            case "name":
                                server.setName(data);
                                break;
                            case "ipport":
                                String[] i = data.split("[:]");
                                server.setIp(i[0]);
                                server.setPort(Integer.parseInt(i[1]));
                            case "game":
                                server.setGame(data);
                        }
                        new_servers.put(uuid,server);
                    }
                    for(Map.Entry<String,GameServer> server : new_servers.entrySet()){
                        if(servers.containsKey(server.getKey())){continue;}
                        ServerInfo serverInfo = server.getValue().build();
                        servers.put(server.getKey(),serverInfo);
                        ProxyServer.getInstance().getServers().put(server.getValue().name,serverInfo);
                    }
                    for(String string : server_uuids) {
                        System.out.println("Removed: "+string);
                        ProxyServer.getInstance().getServers().remove(servers.get(string).getName());
                        servers.remove(string);
                    }
                }
            }
        },1,2, TimeUnit.SECONDS);
    }

    private class GameServer {
        private String uuid;
        private String name;
        private String state;
        private String game;
        private String ip;
        private int port;
        private int playercount;

        public GameServer(String uuid) {this.uuid=uuid;}

        public void setGame(String game) {
            this.game = game;
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
        public ServerInfo build() {
            return ProxyServer.getInstance().constructServerInfo(this.name, InetSocketAddress.createUnresolved(this.ip, this.port),this.game,false);
        }
    }

}
