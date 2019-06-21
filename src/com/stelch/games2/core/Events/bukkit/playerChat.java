package com.stelch.games2.core.Events.bukkit;

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class playerChat implements Listener {

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent e){
        if(BukkitCore.coreChatManager){
            e.setCancelled(true);
            BukkitGamePlayer player = BukkitGamePlayer.getGamePlayer(e.getPlayer().getName());
            Bukkit.broadcastMessage(Text.format(String.format("&7%s[%s] %s&7: %s", player.getRank().getColor(), player.getRank(),player.getUsername(), e.getMessage())));

        }
    }

}
