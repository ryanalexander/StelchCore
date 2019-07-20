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
 *  * @since 19/7/2019
 *
 */

package com.stelch.games2.core.Commands.bungee;

import com.stelch.games2.core.PlayerUtils.ServerConnection;
import com.stelch.games2.core.Utils.Text;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

public class hub extends Command {
    public hub() { super("hub", ""); }

    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ServerInfo server = ServerConnection.sendToHub();
            ((ProxiedPlayer)sender).connect(server);
            sender.sendMessage(Text.build(String.format("&aPortal> &7You have been connected to &e%s&7.", server.getName().toUpperCase())));

        }else {
            sender.sendMessage(Text.build("&aPortal> &7Only players may execute this command."));
        }
    }
}