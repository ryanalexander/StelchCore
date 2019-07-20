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

import redis.clients.jedis.Jedis;

public class API {

    private static String game="unset";
    private static String state="NA";

    public static void setGame(String game){
        API.game=game;
        if(BukkitCore.pool==null){return;}
        try (Jedis jedis = BukkitCore.pool.getResource()){
            jedis.set(String.format("SERVER|%s|game",BukkitCore.uuid),API.game);
        }
    }

    public static void setState(String state){
        API.state=state;
        if(BukkitCore.pool==null){return;}
        try (Jedis jedis = BukkitCore.pool.getResource()){
            jedis.set(String.format("SERVER|%s|state",BukkitCore.uuid),API.state);
        }
    }

    public static String getGame() {
        return game;
    }

    public static String getState() {
        return state;
    }
}
