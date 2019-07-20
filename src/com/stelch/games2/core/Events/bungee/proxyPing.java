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

import com.stelch.games2.core.Utils.JavaUtils;
import com.stelch.games2.core.Utils.Text;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class proxyPing implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent e){
        ServerPing serverPing = e.getResponse();
        String line1 = "&c&m&l᚜ &r  &d&lBlockcade Games &c&m&l ᚛&r";
        String line2 = "&a&lGuardianArena&r &3&l★&r &6&lBlazeWars&r";

        line1 = JavaUtils.center(line1,53+(JavaUtils.chatColorCount(line1,'&',0)*2));
        line2 = JavaUtils.center(line2,53+(JavaUtils.chatColorCount(line2,'&',0)*2));

        serverPing.setDescriptionComponent(new TextComponent(Text.format(line1+"\n"+line2)));
        serverPing.getPlayers().setMax(ProxyServer.getInstance().getOnlineCount()+1);
        e.setResponse(serverPing);
    }
}