package com.stelch.games2.core;

import redis.clients.jedis.Jedis;

public class API {

    private static String game="unset";
    private static String state="NA";

    public static void setGame(String game){
        API.game=game;
        if(BukkitCore.pool==null){return;}
        try (Jedis jedis = BukkitCore.pool.getResource()){
            jedis.set(String.format("SERVER|%s|game",BukkitCore.config.get("uuid")),API.game);
        }
    }

    public static void setState(String state){
        API.state=state;
        if(BukkitCore.pool==null){return;}
        try (Jedis jedis = BukkitCore.pool.getResource()){
            jedis.set(String.format("SERVER|%s|state",BukkitCore.config.get("uuid")),API.state);
        }
    }

    public static String getGame() {
        return game;
    }

    public static String getState() {
        return state;
    }
}