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

package com.stelch.games2.core.Events.bukkit.Game;

import com.stelch.games2.core.Game.Game;
import com.stelch.games2.core.Game.Utils.Managers.TeamManager;
import com.stelch.games2.core.Game.varables.gameState;
import com.stelch.games2.core.Game.varables.teamColors;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatEvent implements Listener {

    Game game;
    public AsyncChatEvent(Game game){this.game=game;}

    @EventHandler
    public void ChatEvent(AsyncPlayerChatEvent e){
        e.setCancelled(true);
        if(game.GameState()== gameState.IN_GAME) {
            teamColors team = game.TeamManager().getTeam(e.getPlayer());
            Bukkit.broadcastMessage(Text.format(String.format("&7[%s&7] &e%s&7: %s", teamColors.valueOf(team.toString().toUpperCase()).getChatColor() + team.toString().toUpperCase(), e.getPlayer().getDisplayName(), e.getMessage())));
        }
    }

}
