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

package com.stelch.games2.core.Events.bungee;

import com.stelch.games2.core.BungeeCore;
import com.stelch.games2.core.PlayerUtils.ProxyGamePlayer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Random;

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
