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

package com.stelch.games2.core.Inventories;

import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameMenu {

    public static Inventory get(Player player){
        Inventory GameMenuInv = Bukkit.createInventory(null,9*3, Text.format("&eGame Menu"));

        Item BlazeWars = new Item(Material.BLAZE_SPAWN_EGG,"&6Blaze Wars");
        BlazeWars.setLore(new String[]{
                "",
                Text.format("&7Assist your team in taking"),
                Text.format("&7over the world. Go island to"),
                Text.format("&7island and destroy cores. But,"),
                Text.format("&7make sure you're careful"),
                Text.format("&7because, that blaze looks"),
                Text.format("&7a little dangerous"),
                "",
                Text.format("&aClick to find a game")
        });
        BlazeWars.setOnClick(new Item.click() {
            @Override
            public void run(Player player) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                player.sendMessage(Text.format("&cGame> &7There are no available servers for this game mode."));
            }
        });

        GameMenuInv.setItem(13,BlazeWars.spigot());

        return GameMenuInv;
    }

}
