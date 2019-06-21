package com.stelch.games2.core;

import com.stelch.games2.core.Events.bungee.playerChangeServer;
import com.stelch.games2.core.Events.bungee.playerJoin;
import com.stelch.games2.core.Events.bungee.playerLeave;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeeCore extends Plugin {

    @Override
    public void onEnable() {
     /*
     * Define Plugin Manager
     */
        PluginManager pm = getProxy().getPluginManager();

        /*
         * Register BungeeCord Events
         */

        getProxy().getPluginManager().registerListener(this,new playerChangeServer());
        getProxy().getPluginManager().registerListener(this,new playerJoin());
        getProxy().getPluginManager().registerListener(this,new playerLeave());
    }

}
