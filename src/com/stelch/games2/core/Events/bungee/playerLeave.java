package com.stelch.games2.core.Events.bungee;

import com.stelch.games2.core.PlayerUtils.ProxyGamePlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class playerLeave implements Listener {

    @EventHandler
    public void ProxyLeave(PlayerDisconnectEvent e){
        ProxyGamePlayer.players.remove(e.getPlayer());
    }

}
