package com.stelch.games2.core;

import com.stelch.games2.core.Commands.bukkit.admin;
import com.stelch.games2.core.Events.bukkit.playerChat;
import com.stelch.games2.core.Events.bukkit.playerJoin;
import com.stelch.games2.core.Events.bukkit.playerLeave;
import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.Utils.configValues;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BukkitCore extends JavaPlugin {

    /*
     * API Changeable options
     */
    public static boolean coreChatManager = true;

    /*
     * Server Configuration
     */
    public static HashMap<configValues, Boolean> config_option = new HashMap<>();

    @Override
    public void onEnable() {
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
    }

}
