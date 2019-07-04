package com.stelch.games2.core.Inventories;


import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.PlayerUtils.ranks;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RankMenu {

    public static Inventory get(BukkitGamePlayer player) {
        Inventory GameMenuInv = Bukkit.createInventory(null,9*3, Text.format(String.format("&eSetting rank for %s",player.getUsername())));

        ItemStack placeholder = new Item(Material.BLACK_STAINED_GLASS_PANE,"&r").setOnClick(new Item.click() {@Override public void run(Player param1Player) { }}).spigot();

        for(int i=0;i<GameMenuInv.getSize();i++){GameMenuInv.setItem(i,placeholder);}
        int i = 10;
        for(ranks rank : ranks.values()){
            Item r = new Item(((player.getRank().equals(rank))?Material.WHITE_STAINED_GLASS_PANE:Material.WHITE_STAINED_GLASS_PANE),Text.format(rank.getColor()+rank.name()));
            if(player.getRank().equals(rank)){r.setEnchanted(true);}
            r.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {
                    player.setRank(rank);
                    p.openInventory(get(player));

                }
            });
            GameMenuInv.setItem(i,r.spigot());
            if(i==8){i++;}
            if(i==17){i++;}
            i++;
        }

        return GameMenuInv;
    }

}
