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
import com.stelch.games2.core.Lang.en;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcessEvent implements Listener {

    Game game;
    public CommandPreProcessEvent(Game game){this.game=game;}

    @EventHandler
    public void CommandPreProcessEvent(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/rl") || e
                .getMessage().startsWith("/reload") || e
                .getMessage().startsWith("/stop") && game.GameState()== gameState.IN_GAME) {
            if((e.getPlayer() instanceof Player)&& BukkitGamePlayer.getGamePlayer(e.getPlayer().getName()).getRank().getLevel()<10){e.getPlayer().sendMessage(Text.format(en.PERM_NO_PERMISSION));return;}

            e.getPlayer().sendMessage(Text.format("&aWarn> &7That command has been delayed by the Mini-Games"));
            e.setCancelled(true);
            game.stop(false);
            Bukkit.getServer().reload();
            Bukkit.getServer().reloadData();
            e.getPlayer().sendMessage(Text.format("&aServer> &7The server has been reloaded"));
        }
    }
}
