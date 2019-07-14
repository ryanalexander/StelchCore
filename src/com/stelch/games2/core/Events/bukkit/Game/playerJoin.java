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
import com.stelch.games2.core.Game.varables.lang;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoin implements Listener {

    Game game;
    public playerJoin(Game game){this.game=game;}

    @org.bukkit.event.EventHandler
    public void playerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        // TODO Scoreboard

        switch(game.GameState()){
            case LOBBY:
                Bukkit.broadcastMessage(Text.format(String.format(lang.GAME_PLAYER_JOIN.get() ,e.getPlayer().getDisplayName(),Bukkit.getOnlinePlayers().size(),game.minPlayers())));
                if(game.canStart()){
                    game.start();
                }
                break;
            case STARTING:
                // Update scoreboard
                Bukkit.broadcastMessage(Text.format(String.format(lang.GAME_PLAYER_JOIN_STARTING.get() ,e.getPlayer().getDisplayName(),Bukkit.getOnlinePlayers().size(),game.minPlayers())));
                break;
            case IN_GAME:
                if(1==1){
                    e.getPlayer().setGameMode(GameMode.SPECTATOR);
                    e.getPlayer().sendTitle(Text.format("&cYou are now a spectator"),"This game is already in progress. You may only spectate.",10,60,10);
                }else {
                    e.getPlayer().kickPlayer(Text.format("&aJOIN> &7This game has already started. You were returned to the lobby."));
                }
                break;
            default:
                e.getPlayer().kickPlayer(Text.format(String.format("&cERROR> &7Failed to join the requested game. [Game-state: %s]",game.GameState())));
                break;
        }

    }
}
