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

import com.stelch.games2.core.Commands.bukkit.admin;
import com.stelch.games2.core.Events.bukkit.playerChat;
import com.stelch.games2.core.Events.bukkit.playerJoin;
import com.stelch.games2.core.Events.bukkit.playerLeave;
import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.Utils.JedisUtils;
import com.stelch.games2.core.Utils.configValues;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

public class BukkitCore extends JavaPlugin {

    public static JedisPool pool=null;
    public static UUID uuid=UUID.randomUUID();
    public static String serverName=null;

    /*
     * API Changeable options
     */
    public static boolean coreChatManager = true;

    /*
     * Ease of access CoreConfig
     */
    public static FileConfiguration config;

    /*
     * Server Configuration
     */
    public static HashMap<configValues, Boolean> config_option = new HashMap<>();

    @Override
    public void onEnable() {

        /*
         * Save default configuration options
         */
        getConfig().options().copyDefaults(true);
        saveConfig();
        config = BukkitCore.this.getConfig();

        /*
         * Define Plugin Manager
         */
        PluginManager pm = Bukkit.getPluginManager();

        /*
         * Register Bukkit Events
         */
        pm.registerEvents(new Item(this), this);
        pm.registerEvents(new playerJoin(), this);
        pm.registerEvents(new playerLeave(), this);
        pm.registerEvents(new playerChat(), this);

        /*
         * Register Bukkit Commands
         */
        getCommand("admin").setExecutor(new admin());

        /*
         * Prevent Weather Cycle and Daylight Cycle
         */
        for(World w : Bukkit.getWorlds()){
            w.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
        }

        /**
         * Connect Jedis client to Redis server
         */
        System.out.println("[Redis] Connecting to redis server");
        JedisUtils.init(pool,this);
        System.out.println("[Redis] Finished connecting to redis server");

        /**
         * Add to Bungee
         */
        System.out.println("[Redis] Pushing server to network.");
        try (Jedis jedis = BukkitCore.pool.getResource()){
            jedis.set(String.format("SERVER|%s|ipport",uuid),getServer().getIp()+":"+getServer().getPort());
            jedis.set(String.format("SERVER|%s|type",uuid),getConfig().getString("server-type"));
            jedis.set(String.format("SERVER|%s|playercount",uuid),getServer().getOnlinePlayers().size()+"");
            jedis.set(String.format("SERVER|%s|game",uuid),API.getGame());
            jedis.set(String.format("SERVER|%s|state",uuid),API.getState());
            jedis.set(String.format("SERVER|%s|last_poll",uuid), Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis()+"");
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                try (Jedis jedis = BukkitCore.pool.getResource()){
                    String name = jedis.get(String.format("SERVER|%s|name",uuid));
                    if(name!=null){
                        getConfig().set("server-name",name);
                        serverName=name;
                        cancel();
                    }else {
                        System.out.println("Failed to get name. Trying again soon.");
                    }
                }
            }
        }.runTaskTimer(this,0L,(20*5));

        new BukkitRunnable(){
            @Override
            public void run() {
                try (Jedis jedis = BukkitCore.pool.getResource()){
                    if(serverName!=null)jedis.set(String.format("SERVER|%s|name",uuid),serverName);
                    jedis.set(String.format("SERVER|%s|ipport",uuid),getServer().getIp()+":"+getServer().getPort());
                    jedis.set(String.format("SERVER|%s|type",uuid),getConfig().getString("server-type"));
                    jedis.set(String.format("SERVER|%s|playercount",uuid),getServer().getOnlinePlayers().size()+"");
                    jedis.set(String.format("SERVER|%s|game",uuid),API.getGame());
                    jedis.set(String.format("SERVER|%s|state",uuid),API.getState());
                    jedis.set(String.format("SERVER|%s|last_poll",uuid), Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis()+"");
                }
            }
        }.runTaskTimer(this,0L,(20*5));
    }

}
