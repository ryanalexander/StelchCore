package com.stelch.games2.core.Events.bungee;

import com.stelch.games2.core.PlayerUtils.ProxyGamePlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class playerJoin implements Listener {

    @EventHandler
    public void ProxyJoin(ServerConnectedEvent e){
        ProxyGamePlayer player;
        if(!(ProxyGamePlayer.players.containsKey(e.getPlayer()))){
            player = new ProxyGamePlayer(e.getPlayer().getUniqueId());
            ProxyGamePlayer.players.put(e.getPlayer(),player);
        }else {
            player=ProxyGamePlayer.players.get(e.getPlayer());
        }

        player.setServer(e.getServer().getInfo());
    }

}
