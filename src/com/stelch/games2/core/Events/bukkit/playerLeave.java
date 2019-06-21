package com.stelch.games2.core.Events.bukkit;

import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        BukkitGamePlayer.players.remove(e.getPlayer());
    }

}
