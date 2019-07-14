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

import com.stelch.games2.core.BukkitCore;
import com.stelch.games2.core.Game.Game;
import com.stelch.games2.core.InventoryUtils.Item;
import com.stelch.games2.core.Lang.en;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class mct implements CommandExecutor {

    JavaPlugin plugin;
    Game game=null;
    public mct(JavaPlugin main, Game game){
        plugin=main;
        this.game=game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if((sender instanceof Player)&& BukkitGamePlayer.getGamePlayer(sender.getName()).getRank().getLevel()<10){sender.sendMessage(Text.format(en.PERM_NO_PERMISSION));return false;}
        if(!(sender instanceof Player)){sender.sendMessage("You must be a player to execute this command.");return false;}
        if(game==null){sender.sendMessage(Text.format("&cMCT> &7Invalid game session, ensure the API is being used correctly."));return false;}

        Player player = (Player)sender;
        Location loc = player.getLocation();
        if(args.length<1){
            sender.sendMessage(new String[]{
                    Text.format("&d----[ &aMap Creator Tools &d]----"),
                    Text.format("&eThis tool was created for Map Creators to"),
                    Text.format("&ehave the ability to specify custom map parameters"),
                    " ",
                    Text.format("&e- &a/mct set &d{spawn/forge/blaze/core/zone} {team/id}"),
                    Text.format("&e- &a/mct add &d{forge} {mid}"),
                    Text.format("&e- &a/mct remove &d{spawn/forge/blaze/core} {team/id}"),
                    "",
                    Text.format("&e- &a/mct list &d{spawner/blaze/core}")

            });
            return false;
        }

        switch(args[0]){
            case "set":
                if(args[1].equalsIgnoreCase("zone")){
                    sender.sendMessage(Text.format("&aMCT> &7Entered zone selection mode."));
                    Item item = new Item(Material.STICK,"&aZone selection tool");
                    break;
                }
                plugin.getConfig().set(String.format("maps.%s.%s.%s.x",loc.getWorld().getName(),args[1].toLowerCase(),args[2].toUpperCase()),Double.parseDouble(""+loc.getX()));
                plugin.getConfig().set(String.format("maps.%s.%s.%s.y",loc.getWorld().getName(),args[1].toLowerCase(),args[2].toUpperCase()),Double.parseDouble(""+loc.getY()));
                plugin.getConfig().set(String.format("maps.%s.%s.%s.z",loc.getWorld().getName(),args[1].toLowerCase(),args[2].toUpperCase()),Double.parseDouble(""+loc.getZ()));
                plugin.getConfig().set(String.format("maps.%s.%s.%s.yaw",loc.getWorld().getName(),args[1].toLowerCase(),args[2].toUpperCase()),Double.parseDouble(""+loc.getYaw()));
                plugin.saveConfig();
                sender.sendMessage(Text.format(String.format("&aMCT> &7Saved &e%s&7 team &e%s&7 location at x:%s y:%s z:%s",args[2].toUpperCase(),args[1],loc.getX(),loc.getY(),loc.getZ())));
                break;
            case "remove":
                sender.sendMessage(Text.format("&aMCT> The &eremove&r command is under construction"));
                sender.sendMessage(Text.format(String.format("&aMCT> &7Removed &e%s&7 location at x:%s y:%s z:%s",args[1],loc.getX(),loc.getY(),loc.getZ())));
                break;
            case "add":
                String key = String.format("maps.%s.%s",((Player) sender).getLocation().getWorld().getName(),args[1].toUpperCase());
                List<String> list = plugin.getConfig().getStringList(key);
                list.add(String.format("%s:%s:%s:%s",loc.getWorld().getName(),Double.parseDouble(""+loc.getX()),Double.parseDouble(""+loc.getY()),Double.parseDouble(""+loc.getZ())));
                plugin.getConfig().set(key, list);
                sender.sendMessage(Text.format(String.format("&aMCT> &7Saved &e%s&7 location at x:&e%s&7 y:&e%s&7 z:&e%s&7 key: &e%s",args[1].toUpperCase(),Double.parseDouble(""+loc.getX()),Double.parseDouble(""+loc.getY()),Double.parseDouble(""+loc.getZ()),key)));
                plugin.saveConfig();
                break;
            case "list":
                sender.sendMessage(Text.format("&aMCT> The &elist&r command is under construction"));
                break;
            case "spawnec":

                break;
            default:
                sender.sendMessage(Text.format("&aMCT> &fThe command specified does not exist."));
        }
        return false;
    }
}
