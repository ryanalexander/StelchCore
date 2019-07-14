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

package com.stelch.games2.core.Events.bukkit.Game;

import com.stelch.games2.core.Game.Game;
import com.stelch.games2.core.Game.varables.gameState;
import com.stelch.games2.core.Game.varables.lang;
import com.stelch.games2.core.Game.varables.teamColors;
import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;

import static org.bukkit.Material.FIRE_CHARGE;
import static org.bukkit.Material.MAGMA_CUBE_SPAWN_EGG;

public class EntityInteract implements Listener {

    Game game;
    public EntityInteract(Game game){this.game=game;}

    @EventHandler
    public void itemPickup(PlayerPickupItemEvent e){
        if(game.SpectatorManager().Spectator(e.getPlayer())){e.setCancelled(true);}
    }

    @EventHandler
    public void InventorySwitch(PlayerItemHeldEvent e){

        /* TODO Fix invisibility potion
        if(game.invis_players.containsKey(e.getPlayer())){
            game.invis_players.get(e.getPlayer()).setItemInHand(e.getPlayer().getInventory().getItem(e.getNewSlot()));
        }
         */

    }

    @EventHandler
    public void EntityDamage(EntityDamageEvent e){
        if(game.GameState().equals(gameState.LOBBY)){e.setCancelled(true);}
    }

    @EventHandler
    public void EntityDamageEntity(EntityDamageByEntityEvent e){
        if(e.getEntity().getType().equals(EntityType.PLAYER)&&game.SpectatorManager().Spectator((Player)e.getEntity())){e.setCancelled(true);}
        if(e.getDamager().getType().equals(EntityType.PLAYER)&&game.SpectatorManager().Spectator((Player)e.getDamager())){e.setCancelled(true);}
        if(e.getEntity() instanceof ArmorStand){
            /*
            TODO Fix invisibility potion
            if(game.invis_players.containsValue(e.getEntity())){
                for(Map.Entry<Player,ArmorStand> d : game.invis_players.entrySet()){
                    if(d.getValue().equals(e.getEntity())){
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.showPlayer(Main.getPlugin(Main.class), d.getKey());
                            d.getValue().remove();
                        }
                        game.invis_players.remove(d.getKey());
                        return;
                    }
                }
            }
           */
        }
    }

    @EventHandler
    public void food(FoodLevelChangeEvent e) { e.setCancelled(true); }

    @EventHandler
    public void EntityInteract(PlayerInteractEvent e){
        if(game.SpectatorManager().Spectator(e.getPlayer())){e.setCancelled(true);}
    }

    @EventHandler
    public void EntityInteractEvent(PlayerInteractEntityEvent e){
        if(game.SpectatorManager().Spectator(e.getPlayer())){e.setCancelled(true);}
        if(game.EntityManager().hasFunction(e.getRightClicked())){
            e.setCancelled(true);
            game.EntityManager().EntityFunction(e.getRightClicked()).run(e.getPlayer());
        }
    }

    @EventHandler
    public void chestOpen(InventoryOpenEvent e){
        if(game.SpectatorManager().Spectator((Player)e.getPlayer())){e.setCancelled(true);}
        if (e.getInventory().getHolder()==null) {return;}
        if(e.getInventory().getType().equals(InventoryType.CHEST)){
            if(game.GameState()==gameState.IN_GAME&&e.getInventory().getLocation().getBlock()!=null) {
                teamColors teamChest = game.TeamManager().getTeamChest(e.getInventory().getLocation().getBlock());
                if (teamChest == null) {
                    return;
                }
                teamColors team = game.TeamManager().getTeam((Player) e.getPlayer());
                if (!(team.equals(teamChest))&&game.TeamManager().getCanRespawn(teamChest)) {
                    e.getPlayer().sendMessage(Text.format(String.format(lang.CHEST_TEAM_NOT_ELIMINATED.get(),game.TeamManager().getTeamColor(teamChest)+teamChest)));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void eggThrow(PlayerEggThrowEvent e){
        e.setHatching(false);
    }

}
