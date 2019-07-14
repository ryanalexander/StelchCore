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

package com.stelch.games2.core.Game.Utils.Functions;

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.Game.Game;
import com.stelch.games2.core.Game.varables.gameState;
import com.stelch.games2.core.Game.varables.teamColors;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class stop {

    public stop(Game game, boolean natural){
        game.GameState(gameState.RESTARTING);
        if(natural){
            doFinishGame(game);
        }else {
            cleanUp(game);
        }
    }

    public void cleanUp(Game game){

        game.BlockManager().doRollback();
        Bukkit.shutdown();
    }

    public void doFinishGame(Game game){
        teamColors winner = game.TeamManager().getActive_teams().get(0);
        Bukkit.broadcastMessage(Text.format(String.format("&eCongratulations to %s&e team! You won!", game.TeamManager().getTeamColor(winner) + winner)));
        for (HashMap.Entry<Player, teamColors> ent : game.TeamManager().getPlayers().entrySet()) {
            if (ent.getValue().equals(winner)) {
                Location loc = ent.getKey().getLocation();
                Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();

                fwm.setPower(2);
                fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

                fw.setFireworkMeta(fwm);
                fw.detonate();

                for (int i = 0; i < 24; i++) {
                    Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
                    fw2.setFireworkMeta(fwm);
                }
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Text.format("&aGame> &7The game has finished."));
                    player.kickPlayer("[GAMESTATE] The game has finished");
                }
                game.stop(false);
            }
        }.runTaskLater(BukkitCore.getPlugin(BukkitCore.class), 200);
    }

}
