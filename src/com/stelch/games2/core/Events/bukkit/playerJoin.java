package com.stelch.games2.core.Events.bukkit;

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.Inventories.GameMenu;
import com.stelch.games2.core.Inventories.Profile;
import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.Utils.Text;
import com.stelch.games2.core.Utils.configValues;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class playerJoin implements Listener {

    @EventHandler
    public void PreJoin(AsyncPlayerPreLoginEvent e){
        BukkitGamePlayer player = new BukkitGamePlayer(e.getName());

        if(BukkitCore.config_option.getOrDefault(configValues.WHITELIST,false)){
            if(player.getRank().getLevel()<=8){
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,Text.format("&aPortal> &7This server's whitelist is currently enabled."));
            }
        }
    }

    @EventHandler
    public void PlayerJoin(org.bukkit.event.player.PlayerJoinEvent e){
        BukkitGamePlayer player = new BukkitGamePlayer(e.getPlayer().getName());

        e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20);
        BukkitGamePlayer.players.put(e.getPlayer(),player);
        e.setJoinMessage("");
    }

}
