package com.stelch.games2.core.Commands.bukkit;

import com.google.common.base.Enums;
import com.stelch.games2.core.Inventories.RankMenu;
import com.stelch.games2.core.Lang.en;
import com.stelch.games2.core.PlayerUtils.BukkitGamePlayer;
import com.stelch.games2.core.PlayerUtils.ranks;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class admin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if((sender instanceof Player)&& BukkitGamePlayer.getGamePlayer(sender.getName()).getRank().getLevel()<10){sender.sendMessage(Text.format(en.PERM_NO_PERMISSION));return false;}

        switch(((args.length>=1)?args[0]:"other")) {
            case "getuser":
                BukkitGamePlayer player = BukkitGamePlayer.getGamePlayer(args[1]);
                if(!(player.isStored())){sender.sendMessage(Text.format(String.format("&cAdmin> &7It seems that '&e%s&7' has never joined this server.",args[1])));return false;}

                sender.sendMessage("Name: "+player.getUsername());
                sender.sendMessage("UUID: "+player.getUuid());
                sender.sendMessage("Rank: "+player.getRank());
                sender.sendMessage("online: "+player.isonline());
                break;
            case "setrank":
                if(!(BukkitGamePlayer.getGamePlayer(args[1]).isStored())){sender.sendMessage(Text.format(String.format("&cAdmin> &7It seems that '&e%s&7' has never joined this server.",args[1])));return false;}
                if(args.length>2){
                    if(Enums.getIfPresent(ranks.class,args[2]).isPresent()){
                        BukkitGamePlayer.getGamePlayer(args[1]).setRank(ranks.valueOf(args[2].toUpperCase()));
                        sender.sendMessage(Text.format(String.format("&cAdmin> &7You have set &e%s&7's rank to &e%s&7.",args[1],args[2].toUpperCase())));
                    }else {
                        sender.sendMessage(Text.format(String.format("&cAdmin> &7It seems '&e%s&7' is not a valid rank!",args[2].toUpperCase())));
                    }
                }else if(args.length==2){
                    ((Player)sender).openInventory(RankMenu.get(BukkitGamePlayer.getGamePlayer(args[1])));
                }else {
                    sender.sendMessage(Text.format("&cAdmin> &7Usage: '&e/admin setrank {user} [rank]&7'"));
                }
                break;
            default:
                sender.sendMessage(Text.format("&cAdmin> &7Invalid arguments"));
        }

        return false;
    }
}
