package com.stelch.games2.core.Events.bukkit;

import com.stelch.games2.core.Inventories.GameMenu;
import com.stelch.games2.core.Inventories.Profile;
import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class playerJoin implements Listener {

    @EventHandler
    public void PlayerJoin(org.bukkit.event.player.PlayerJoinEvent e){
        BukkitGamePlayer.players.put(e.getPlayer(),new BukkitGamePlayer(e.getPlayer().getName()));
        e.setJoinMessage("");
    }

}
