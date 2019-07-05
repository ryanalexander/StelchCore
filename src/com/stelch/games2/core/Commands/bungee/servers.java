package com.stelch.games2.core.Commands.bungee;

import com.stelch.games2.core.Utils.Text;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

public class servers extends Command {
    public servers() { super("servers", ""); }

    public void execute(CommandSender sender, String[] args) {
        Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
        ComponentBuilder reply = new ComponentBuilder(new TextComponent(Text.build("&aServers (&f"+servers.size()+"&a):"))).reset();
        for(ServerInfo server : servers.values()){
            reply.append(new TextComponent(Text.build("&b"+server.getName()+"&7, ")));
        }

        sender.sendMessage(reply.create());
    }
}