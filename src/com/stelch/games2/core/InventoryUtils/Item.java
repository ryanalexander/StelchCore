package com.stelch.games2.core.InventoryUtils;

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Item implements Listener {
    public static HashMap<Item, click> actions = new HashMap<>();

    BukkitCore plugin;
    ItemStack is;
    ItemMeta im;

    public Item (BukkitCore main){
        this.plugin=main;
    }

    public Item (Material material, String name) {
        this.is=new ItemStack(material);
        im=this.is.getItemMeta();
        im.setDisplayName(Text.format(name));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    public void setSkull(UUID player){
        if(is.getType().equals(Material.PLAYER_HEAD)){
            SkullMeta meta = (SkullMeta) im;
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(player));
            this.im=meta;
        }
    }

    public void setName(String name){ this.im.setDisplayName(Text.format(name)); }

    public void setMaterial(Material mat){ this.is.setType(mat); }

    public void setAmount(int amount){
        is.setAmount(amount);
    }

    public void setEnchanted(boolean enchanted) { if(!enchanted){for(Enchantment enchantment : this.im.getEnchants().keySet()){this.im.removeEnchant(enchantment);}return;}im.addEnchant(Enchantment.ARROW_DAMAGE,1,true);im.addItemFlags(ItemFlag.HIDE_ENCHANTS); }

    public void setLore (String[] lines){
        ArrayList<String> lines2 = new ArrayList<>();
        for(String line : lines){
            lines2.add(Text.format(line));
        }
        im.setLore(lines2);
    }

    public Item setOnClick(click onClick){
        if(onClick!=null)
            actions.put(this,onClick);
        return this;
    }

    public ItemStack spigot() {
        this.is.setItemMeta(this.im);
        return this.is;
    }



    public static interface click {
        void run(Player param1Player);
    }

    @EventHandler
    public void EntityInteract(PlayerInteractEvent e){
        for (Map.Entry<Item, Item.click> is : actions.entrySet()) {
            if(is.getKey().spigot().equals(e.getItem())){
                if(is.getValue()!=null)
                    is.getValue().run((Player)e.getPlayer());
                e.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        for (Map.Entry<Item, Item.click> is : actions.entrySet()) {
            if(is.getKey().spigot().equals(e.getCurrentItem())){
                if(is.getValue()!=null)
                    is.getValue().run((Player)e.getWhoClicked());
                e.setCancelled(true);
                return;
            }
        }
    }

}
