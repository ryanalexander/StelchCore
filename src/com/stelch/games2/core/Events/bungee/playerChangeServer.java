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

import com.stelch.games2.core.PlayerUtils.ProxyGamePlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class playerChangeServer implements Listener {
    @EventHandler
    public void playerChangeServer(ServerSwitchEvent e) {
        ProxyGamePlayer.players.get(e.getPlayer()).setServer(e.getPlayer().getServer().getInfo());
    }
}