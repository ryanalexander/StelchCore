package com.stelch.games2.core.PlayerUtils;

import com.stelch.games2.core.Utils.SQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

public class BukkitGamePlayer {
    public static HashMap<Player, BukkitGamePlayer> players = new HashMap<>();

    private Player player;


    private UUID uuid;

    private String username=null;

    private boolean hide_players;

    private ranks rank;

    private String last_seen;

    private boolean stored = false;

    private boolean retrying = false;

    public BukkitGamePlayer(String name){
        Player p = Bukkit.getServer().getPlayer(name);
        this.username=name;
        if(p!=null) {
            this.username=p.getName();
            this.player=p;
        }
        resolvePlayer();
    }

    @Deprecated
    public BukkitGamePlayer(UUID UUID){
        Player p = Bukkit.getServer().getPlayer(UUID);
        if(p!=null) {
            this.username=p.getName();
            this.player=p;
        }
        resolvePlayer();
    }

    public void resolvePlayer() {
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        ResultSet results = sql.query(String.format("SELECT * FROM `players` WHERE `%s` = '%s'","username",this.username));
        try {
            while (results.next()) {
                this.username=results.getString("username");
                this.uuid=UUID.fromString(results.getString("uuid"));
                this.rank=ranks.valueOf(results.getString("rank").toUpperCase());
                this.last_seen=results.getString("last_seen");
                this.stored=true;
            }
        } catch (Exception e){
            if(retrying){
                this.username="undefined";
                this.player.kickPlayer("Technical Issues. Please try again later.");
                e.printStackTrace();
                this.retrying=false;
            }else {
                resolvePlayer();
            }
        }
    }

    public boolean isStored() {
        return stored;
    }

    public Player getPlayer() {
        return player;
    }

    public ranks getRank() {
        return rank;
    }
    public void setRank(ranks rank) {
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        sql.query(String.format("UPDATE `players` SET `rank`='%s' WHERE `uuid`='%s';",rank.name().toUpperCase(),this.uuid),true);
        this.rank=rank;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Player legacy() {return this.player;}

    public boolean hasPendingRequest(BukkitGamePlayer player) {
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

    public boolean isFriends(BukkitGamePlayer player){
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

    public void doAcceptRequest(BukkitGamePlayer player){
        SQL sql = new SQL("35.192.213.70",3306,"root","Garcia#02","games");
        sql.query(String.format("UPDATE `relationships` SET `active`='1' WHERE `player`='%s' AND `target`='%s';",player.uuid,this.uuid),true);
        sql.query(String.format("INSERT INTO `relationships` (`player`,`target`,`state`) WHERE ('%s','%s','%s')",this.uuid,player.uuid,1),true);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean isonline() {return ((player!=null)&&player.isOnline());}

    public static BukkitGamePlayer getGamePlayer(String player){
        Player target = Bukkit.getServer().getPlayer(player);
        BukkitGamePlayer gamePlayer;
        if(players.containsKey(target)){
            gamePlayer= players.get(target);
        }else {
            gamePlayer=new BukkitGamePlayer(player);
        }
        return gamePlayer;
    }

}
