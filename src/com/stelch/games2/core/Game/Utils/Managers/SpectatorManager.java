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

package com.stelch.games2.core.Game.Utils.Managers;

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.Game.Game;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SpectatorManager {
    private Game game;

    public SpectatorManager(Game game){this.game=game;}

    private static HashMap<Player, Spectator> spectators = new HashMap<>();

    public boolean Spectator(Player player){ return spectators.containsKey(player); }

    static class Spectator {

        Player player;
        public Spectator(Player player) {
            this.player=player;
            for(Player p : Bukkit.getOnlinePlayers()){
                p.hidePlayer(BukkitCore.getPlugin(BukkitCore.class),this.player);
            }

            spectators.put(player,this);
            player.getInventory().clear();
            player.setAllowFlight(true);
            player.setSilent(true);

        }

        public void leave() {
            for(Player p : Bukkit.getOnlinePlayers()){
                player.showPlayer(BukkitCore.getPlugin(BukkitCore.class),this.player);
            }
            spectators.remove(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.setSilent(false);
            if(player.getGameMode().equals(GameMode.SURVIVAL))player.setAllowFlight(false);
        }
    }
}
