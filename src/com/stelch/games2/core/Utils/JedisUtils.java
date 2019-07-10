package com.stelch.games2.core.Utils;

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.BungeeCore;

public class JedisUtils {

    public static void init(redis.clients.jedis.JedisPool pool, BungeeCore c) {
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        System.out.println("[Redis] Connection began and cached.");
        c.pool = new redis.clients.jedis.JedisPool("127.0.0.1");
        Thread.currentThread().setContextClassLoader(previous);
    }

    public static void init(redis.clients.jedis.JedisPool pool, BukkitCore c) {
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        System.out.println("[Redis] Connection began and cached.");
        c.pool = new redis.clients.jedis.JedisPool("127.0.0.1");
        Thread.currentThread().setContextClassLoader(previous);
    }

    public static void end(redis.clients.jedis.JedisPool pool) {
        pool.close();
    }

}