package com.stelch.games2.core.PlayerUtils;


import com.stelch.games2.core.BungeeCore;
import com.stelch.games2.core.Utils.SQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import redis.clients.jedis.Jedis;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

public class ProxyGamePlayer {

    public static HashMap<ProxiedPlayer, ProxyGamePlayer> players = new HashMap<>();

    private ProxiedPlayer player;

    private ServerInfo server;

    private UUID uuid;

    private String username=null;

    private ranks rank;

    private String last_seen;

    private boolean stored = false;

    private boolean retrying = false;

    public ProxyGamePlayer(String name){
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(name);
        this.username=name;
        if(p!=null) {
            this.uuid=p.getUniqueId();
            this.username=p.getName();
            this.player=p;
        }
        resolvePlayer();
    }
    public ProxyGamePlayer(UUID UUID){
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(UUID);
        this.uuid=UUID;
        if(p!=null) {
            this.username=p.getName();
            this.player=p;
        }
        resolvePlayer();
    }

    /**
     * Jedis integration. This version will <br/>
     * be used for the "infrastructure upgrade" as DB tech will be changed.
     */
    /*
    public void resolvePlayer() {
        try (Jedis jedis = BungeeCore.pool.getResource()){
            System.out.println("[ProxyPlayer] Fetched player profile for "+this.uuid);
            String username;
            String rank;
            username=jedis.get(String.format("PLAYER|%s|name",this.uuid));
            rank=jedis.get(String.format("PLAYER|%s|rank",this.uuid));
            if(username==null){
                createPlayer();return;}
            this.username=username;
            this.rank=ranks.valueOf(rank);
            this.stored=true;
            System.out.println(String.format("[ProxyPlayer] UUID: %s USERNAME: %s RANK: %s",this.uuid,this.username,this.rank.name()));
        }
    }

    private void createPlayer() {
        if(!(ProxyServer.getInstance().getPlayer(this.uuid)!=null||ProxyServer.getInstance().getPlayer(this.username)!=null))
            return;
        System.out.println("[ProxyPlayer] Created Redis instance for "+this.username);
        try (Jedis jedis = BungeeCore.pool.getResource()){
            jedis.set(String.format("PLAYER|%s|name",this.uuid),this.username);
            jedis.set(String.format("PLAYER|%s|rank",this.uuid),ranks.MEMBER.name());
        }
    }*/
    public void resolvePlayer() {
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        ResultSet results = sql.query(String.format("SELECT * FROM `players` WHERE `uuid` = '%s'",this.uuid));
        try {
            while (results.next()) {
                this.username=results.getString("username");
                this.rank=ranks.valueOf(results.getString("rank").toUpperCase());
                this.stored=true;
            }
        } catch (Exception e){
            if(retrying){
                this.username="undefined";
                e.printStackTrace();
                this.retrying=false;
            }else {
                createPlayer();
                this.retrying=true;
                resolvePlayer();
            }
        }
        if(!this.stored){
            createPlayer();
            resolvePlayer();
        }
    }

    private void createPlayer() {
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        sql.query(String.format("INSERT INTO `games`.`players` (`username`, `uuid`, `rank`) VALUES ('%s', '%s', '%s')",this.player.getName(),this.uuid,ranks.MEMBER),true);
    }

    public boolean isStored() {
        return stored;
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public ranks getRank() {
        return rank;
    }

    public ServerInfo getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    public void setRank(ranks rank) {
        this.rank = rank;
    }

    public void setServer(ServerInfo server) {
        this.server = server;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ProxiedPlayer legacy() {return this.player;}

    public boolean hasPendingRequest(ProxyGamePlayer player) {
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        ResultSet data = sql.query(String.format("SELECT * FROM `relationships` WHERE `target`='%s' AND `player`='%s' AND `state`='0';",this.uuid,player.uuid));
        try {
            while(data.next()){
                sql.close();
                return true;
            }
        }catch (Exception e){
            sql.close();
            return false;
        }
        sql.close();
        return false;
    }

    public boolean isFriends(ProxyGamePlayer player){
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        ResultSet data = sql.query(String.format("SELECT * FROM `relationships` WHERE `player`='%s' AND `target`='%s' AND `state`='1';",this.uuid,player.uuid));
        try {
            while(data.next()){
                sql.close();
                return true;
            }
        }catch (Exception e){
            sql.close();
            return false;
        }
        sql.close();
        return false;
    }

    public void doAcceptRequest(ProxyGamePlayer player){
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        sql.query(String.format("UPDATE `relationships` SET `active`='1' WHERE `player`='%s' AND `target`='%s';",player.uuid,this.uuid),true);
        sql.query(String.format("INSERT INTO `relationships` (`player`,`target`,`state`) WHERE ('%s','%s','%s')",this.uuid,player.uuid,1),true);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean isonline() {return ((player!=null)&&player.isConnected());}

    public static ProxyGamePlayer getProxyGamePlayer(String player){
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(player);
        ProxyGamePlayer ProxyGamePlayer;
        if(players.containsKey(target)){
            ProxyGamePlayer=players.get(target);
        }else {
            ProxyGamePlayer=new ProxyGamePlayer(player);
        }
        return ProxyGamePlayer;
    }

    public static ProxyGamePlayer getProxyGamePlayer(UUID player){
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(player);
        ProxyGamePlayer ProxyGamePlayer;
        if(players.containsKey(target)){
            ProxyGamePlayer=players.get(target);
        }else {
            ProxyGamePlayer=new ProxyGamePlayer(player);
        }
        return ProxyGamePlayer;
    }

}