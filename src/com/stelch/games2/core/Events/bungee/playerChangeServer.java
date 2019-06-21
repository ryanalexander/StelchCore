package com.stelch.games2.core.Events.bungee;

import com.stelch.games2.core.PlayerUtils.ProxyGamePlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class playerChangeServer implements Listener {
    @EventHandler
    public void playerChangeServer(ServerSwitchEvent e) {
        ProxyGamePlayer.players.get(e.getPlayer()).setServer(e.getPlayer().getServer().getInfo());
    }
}