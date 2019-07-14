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
import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.EntityTNTPrimed;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftTNTPrimed;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.CraftItemEvent;

import java.lang.reflect.Field;

public class blockPlace implements Listener {

    Game game;
    public blockPlace(Game game){this.game=game;}

    @EventHandler
    public void BlockFromToEvent(BlockFromToEvent e){
        if(game.GameState()== gameState.IN_GAME)
            game.BlockManager().update(e.getBlock().getLocation(),e.getBlock());
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e){
        if(game.SpectatorManager().Spectator(e.getPlayer())){e.setCancelled(true);}
        if(game.GameState()==gameState.IN_GAME){
            game.BlockManager().update(e.getBlock().getLocation(),e.getBlock());

            if(e.getBlock().getType()==Material.TNT){
                e.getBlock().setType(Material.AIR);
                TNTPrimed tnt = (TNTPrimed)e.getBlock().getLocation().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
                EntityTNTPrimed nmsTNT = (EntityTNTPrimed) (((CraftTNTPrimed) tnt).getHandle());
                try {
                    Field sourceField = EntityTNTPrimed.class.getDeclaredField("source");
                    sourceField.setAccessible(true);
                    sourceField.set(nmsTNT, (EntityLiving)(((CraftLivingEntity) e.getPlayer()).getHandle()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                tnt.setFuseTicks(43);
            }
        }
    }

    @EventHandler
    public void craft(CraftItemEvent e){
        if(game.GameState()==gameState.IN_GAME)
            e.setCancelled(true);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e){
        if(game.SpectatorManager().Spectator(e.getPlayer())){e.setCancelled(true);}
        if(game.GameState()==gameState.IN_GAME){
            if(e.getBlock().getType().equals(Material.CLAY)){
                e.setDropItems(false);
                Item item = new Item(Material.CLAY_BALL,"&bWell... This is useless");
                item.setAmount(1);
                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(),item.spigot());
            }
            if(!(game.BlockManager().canBreakBlock(e.getBlock()))){
                e.getPlayer().sendMessage(Text.format(lang.BLOCK_NOT_BREAKABLE.get()));
                e.setCancelled(true);
            }else {
                if(game.TeamManager().isCore(e.getBlock())) {
                    if(game.TeamManager().getCore(game.TeamManager().getTeam(e.getPlayer())) .equals(e.getBlock())){
                        e.getPlayer().sendMessage(Text.format(lang.BLOCK_NOT_BREAKABLE_SABOTAGE.get()));
                        e.setCancelled(true);
                        return;
                    }
                    if (game.TeamManager().getCanRespawn(game.TeamManager().getCore(e.getBlock()))) {
                        e.setCancelled(true);
                        teamColors team = game.TeamManager().getCore(e.getBlock());
                        game.TeamManager().setCantRespawn(team, true);
                        e.getPlayer().getWorld().strikeLightning(e.getBlock().getLocation());
                        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                        e.getBlock().setType(Material.AIR);
                        //game.getScoreboard().editLine(game.getTeamManager().getScoreboardLine(team),(game.getTeamManager().getTeamColor(team)+"&l"+team.toString().charAt(0)+"&r "+(capitalizeFirstLetter(team.toString())+": &c&l✗")));

                        Bukkit.broadcastMessage(Text.format(String.format(lang.GAME_CORE_DESTROYED.get(), game.TeamManager().getTeamColor(team)+team)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void tntExplode(EntityExplodeEvent e){
        for(Block b : e.blockList()){
            if(game.BlockManager().canBreakBlock(b)){
                game.BlockManager().update(b.getLocation(),b);
                if(b.getType()==Material.GLASS||game.TeamManager().isCore(b)){
                    continue;
                }
                b.setType(Material.AIR);
            }
        }
        e.setCancelled(true);
    }


    private static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }

}
