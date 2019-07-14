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
        System.out.println("test");
        ServerPing serverPing = e.getResponse();
        serverPing.setDescriptionComponent(new TextComponent(Text.build("&aStelch Games &c[testing] &f[L1]")));
        serverPing.getPlayers().setMax(ProxyServer.getInstance().getOnlineCount()+2);
        e.setResponse(serverPing);
    }
}