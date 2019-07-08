package com.stelch.games2.core.Events.bungee;

import com.stelch.games2.core.BungeeCore;
import com.stelch.games2.core.Utils.Text;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Random;

public class playerKick implements Listener {

    @EventHandler
    public void onKick(ServerKickEvent e){
        e.setState(ServerKickEvent.State.CONNECTED);
        e.setCancelled(true);
        int hubs = 0;
        for(ServerInfo server : BungeeCore.servers.values()){

        }
        int rand = (new Random()).nextInt();
        ServerInfo server = ProxyServer.getInstance().getServers().get("hub01");
        e.setCancelServer(server);
        if((new TextComponent(e.getKickReasonComponent()).toPlainText()).toLowerCase().equalsIgnoreCase("[GAMESTATE] The game has finished")){
            e.getPlayer().sendMessage(Text.build(String.format("&aPortal> &7Returned to &e%s&7 as your last game finished.",server.getName())));
        }else {
            e.getPlayer().sendMessage(Text.build("&aPortal> &7Returned to lobby as you were kicked."));
            e.getPlayer().sendMessage(e.getKickReasonComponent());
        }
    }
}