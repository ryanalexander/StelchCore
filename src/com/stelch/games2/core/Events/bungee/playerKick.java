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
import com.stelch.games2.core.Utils.Text;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class playerKick implements Listener {

    @EventHandler
    public void onKick(ServerKickEvent e){
        e.setState(ServerKickEvent.State.CONNECTED);
        e.setCancelled(true);
        ArrayList<ServerInfo> hubs = new ArrayList<>();
        for(ServerInfo server : BungeeCore.servers.values()){
            System.out.println(String.format("Found %s : ishub? [%s]",server.getName(),server.getName().toLowerCase().startsWith("hub")));
            if(e.getKickedFrom().equals(server))continue;
            if(server.getName().toLowerCase().startsWith("hub")){
                hubs.add(server);
            }
        }
        int rand = ThreadLocalRandom.current().nextInt(0, hubs.size() + 1);
        ServerInfo server = hubs.get(rand);
        e.setCancelServer(server);
        if((new TextComponent(e.getKickReasonComponent()).toPlainText()).toLowerCase().equalsIgnoreCase("[GAMESTATE] The game has finished")){
            e.getPlayer().sendMessage(Text.build(String.format("&aPortal> &7Returned to &e%s&7, as your last game finished.",server.getName())));
        }else {
            e.getPlayer().sendMessage(Text.build(String.format("&aPortal> &7You have been sent to &e%s&7, as you were kicked.",server.getName())));
            e.getPlayer().sendMessage(e.getKickReasonComponent());
        }
    }
}