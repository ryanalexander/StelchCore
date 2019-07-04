package com.stelch.games2.core.Inventories;

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.PlayerUtils.ranks;
import com.stelch.games2.core.Utils.Text;
import com.stelch.games2.core.Utils.configValues;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ServerConfig {

    public static Inventory get() {
        Inventory GameMenuInv = Bukkit.createInventory(null,9*3, Text.format("&cServer Configuration Menu"));

        ItemStack placeholder = new Item(Material.BLACK_STAINED_GLASS_PANE,"&r").setOnClick(new Item.click() {@Override public void run(Player param1Player) { }}).spigot();

        for(int i=0;i<GameMenuInv.getSize();i++){GameMenuInv.setItem(i,placeholder);}

        Item whitelist = new Item((whitelist_state()?Material.LIME_STAINED_GLASS_PANE:Material.RED_STAINED_GLASS_PANE),(whitelist_state()?"&aWhitelist enabled":"&cWhitelist disabled"));

        whitelist.setOnClick(new Item.click() {
            @Override
            public void run(Player param1Player) {
                boolean new_value = !whitelist_state();
                BukkitCore.config_option.put(configValues.WHITELIST,new_value);
                param1Player.playSound(param1Player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                param1Player.sendMessage(Text.format(String.format("&cAdmin> &7You have %s the whitelist",(new_value?"&aenabled&7":"&4disabled&7"))));
                param1Player.openInventory(ServerConfig.get());
            }
        });

        GameMenuInv.setItem(10,whitelist.spigot());
        return GameMenuInv;
    }

    private static boolean whitelist_state() {
        return BukkitCore.config_option.getOrDefault(configValues.WHITELIST,false);
    }

}