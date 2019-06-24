package com.stelch.games2.core.Inventories;

import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Profile {

    public static Inventory get(Player player){
        Inventory GameMenuInv = Bukkit.createInventory(null,9*6, Text.format("&e"+player.getName()+"'s Profile"));


        ItemStack placeholder = new Item(Material.BLACK_STAINED_GLASS_PANE,"&r").setOnClick(new Item.click() {@Override public void run(Player param1Player) { }}).spigot();
        for(int i=9;i<18;i++){GameMenuInv.setItem(i,placeholder);}

        Item me = new Item(Material.PLAYER_HEAD,"&a"+player.getName());
        me.setSkull(player.getUniqueId());
        me.setLore(new String[]{
                "",
                "&bRank: "+BukkitGamePlayer.getGamePlayer(player.getName()).getRank().getColor()+ BukkitGamePlayer.getGamePlayer(player.getName()).getRank()
        });
        me.setOnClick(new Item.click() {@Override public void run(Player player) {}});

        Item Friends = new Item(Material.PLAYER_HEAD,"&aFriends");
        Friends.setLore(new String[]{"","Click to view the Friends menu"});
        Friends.setOnClick(new Item.click() {@Override public void run(Player player) {}});



        GameMenuInv.setItem(3,me.spigot());
        GameMenuInv.setItem(4,Friends.spigot());

        return GameMenuInv;
    }

}
