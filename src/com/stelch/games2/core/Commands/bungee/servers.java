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

package com.stelch.games2.core.Commands.bungee;

import com.stelch.games2.core.Utils.Text;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
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
        ComponentBuilder reply = new ComponentBuilder(new TextComponent(Text.build(String.format("&aPortal> &7There are &e%s&7 online servers.",servers.size())))).reset();
        if(servers.size()<10) {
            reply.append(Text.format("&7[ "));
            for (ServerInfo server : servers.values()) {
                TextComponent component = new TextComponent(Text.build("&e" + server.getName() + " "));
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.build("&aClick to connect.")));
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "server " + server.getName()));
                reply.append(component);
            }
            reply.append(Text.format("&7]"));
        }

        sender.sendMessage(reply.create());
    }
}