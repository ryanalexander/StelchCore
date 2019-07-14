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

package com.stelch.games2.core.Game.Utils.Functions;
import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.Game.Game;
import com.stelch.games2.core.Game.varables.gameState;
import com.stelch.games2.core.Game.varables.lang;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class start {
    /**
     *
     * @param game instance of Game
     * @see Game instance
     * @since 14/07/2019
     */
    public start(Game game) {
        game.GameState(gameState.STARTING);
        doCountdown(game,10);
    }

    /**
     *
     * @param game instance of Game
     * @param time (seconds) till game should start
     * @see Game instance
     * @since 14/07/2019
     */
    public void doCountdown(Game game, int time){
        // Check game is in lobby (Don't start countdown if already started)
        if(!(game.GameState().equals(gameState.LOBBY))){return;}

        // Main counter thread
        new BukkitRunnable(){
            int counter = time;
            @Override
            public void run() {
                if (game.GameState() != gameState.STARTING) { cancel();return; }

                if (counter == 10 || counter < 6) {
                    Bukkit.broadcastMessage(String.format(lang.GAME_BEGIN_IN.get(), counter));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                    }
                }
                if(counter<=0){
                    cancel();
                }
                counter--;
            }
        }.runTaskTimer(BukkitCore.getPlugin(BukkitCore.class),0L,20L);
    }
}
