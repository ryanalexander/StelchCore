package com.stelch.games2.core;

import com.stelch.games2.core.Commands.bukkit.admin;
import com.stelch.games2.core.Events.bukkit.playerChat;
import com.stelch.games2.core.Events.bukkit.playerJoin;
import com.stelch.games2.core.Events.bukkit.playerLeave;
import com.stelch.games2.core.InventoryUtils.Item;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitCore extends JavaPlugin {

    /*
     * API Changeable options
     */
    public static boolean coreChatManager = true;

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
    }

}