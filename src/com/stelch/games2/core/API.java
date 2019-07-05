package com.stelch.games2.core;

import redis.clients.jedis.Jedis;

public class API {

    public static void setGame(String game){
        try (Jedis jedis = BukkitCore.pool.getResource()){
            jedis.set(String.format("SERVER|%s|game",BukkitCore.config.get("uuid")),game);
        }
    }

    public static void setState(String state){
        try (Jedis jedis = BukkitCore.pool.getResource()){
            jedis.set(String.format("SERVER|%s|state",BukkitCore.config.get("uuid")),state);
        }
    }

}
