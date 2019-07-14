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

package com.stelch.games2.core.Commands.bukkit;

import com.stelch.games2.core.Game.Game;
import com.stelch.games2.core.Game.varables.gameState;
import com.stelch.games2.core.Lang.en;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class game implements CommandExecutor {

    JavaPlugin plugin;
    Game game=null;
    public game(JavaPlugin main, Game game){
        plugin=main;
        this.game=game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!(sender instanceof Player)){sender.sendMessage("You must be a player to execute this command.");return false;}
        if((sender instanceof Player)&& BukkitGamePlayer.getGamePlayer(sender.getName()).getRank().getLevel()<10){sender.sendMessage(Text.format(en.PERM_NO_PERMISSION));return false;}

        Player player = (Player)sender;
        if(args.length<1){
            sender.sendMessage(new String[]{
                    Text.format("&d----[ &cAdmin &d]----"),
                    " ",
                    Text.format("&e- &a/game &d{start/stop/state}"),

            });
            return false;
        }
            switch(args[0]){
                case "stop":
                    if(args.length>=2&&args[1].contains("-f")){game.stop(false);return false;}
                    if(game.GameState()== gameState.IN_GAME){
                        game.stop(true);
                    }else {
                        player.sendMessage(Text.format("&aADMIN> &7There is no current game in progress."));
                    }
                    break;
                case "start":
                    if(args.length>=2&&args[1].contains("-f")){game.start();return false;}
                    if(game.GameState()==gameState.LOBBY){
                        game.start();
                    }else {
                        player.sendMessage(Text.format("&aADMIN> &7There is already a game in progress."));
                    }
                    break;
                case "state":
                    player.sendMessage(Text.format(String.format("&aADMIN> &7The current GameState is \"&e%s&7\".",game.GameState())));
                    break;
                default:
                    sender.sendMessage(Text.format("&aADMIN> &fThe command specified does not exist."));
            }
            return false;
    }
}
