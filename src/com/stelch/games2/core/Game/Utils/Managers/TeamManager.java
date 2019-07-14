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

package com.stelch.games2.core.Game.Utils.Managers;

import com.stelch.games2.core.Game.Game;
import com.stelch.games2.core.Game.varables.lang;
import com.stelch.games2.core.Game.varables.teamColors;
import com.stelch.games2.core.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamManager {

    private HashMap<Player, teamColors> players = new HashMap<>();
    private ArrayList<teamColors> active_teams = new ArrayList<>();
    private HashMap<teamColors, ArrayList<Player>> team_players = new HashMap<>();

    private HashMap<teamColors, Block> cores = new HashMap<>();
    private ArrayList<teamColors> cantRespawn = new ArrayList<>();

    private HashMap<teamColors, Integer> scoreboard_lines = new HashMap<>();

    private HashMap<teamColors, Location> forge_location = new HashMap<>();
    private HashMap<teamColors, Integer> spawner_level = new HashMap<>();

    private HashMap<teamColors, Boolean> healPool = new HashMap<>();

    private HashMap<teamColors, Block> team_chests = new HashMap<>();

    private HashMap<teamColors, Entity> team_blaze = new HashMap<>();
    private HashMap<Entity,Long> team_blaze_cooldown = new HashMap<>();

    private Game game;

    public TeamManager(Game game){this.game=game;}


    public teamColors getTeam(Player p){
        return players.get(p);
    }
    public boolean hasTeam(Player p) { return players.containsKey(p); }

    public ArrayList<teamColors> getActive_teams() { return this.active_teams; }

    public void setCantRespawn (teamColors team, boolean state) {
        if(state){
            cantRespawn.add(team);
        }else {
            cantRespawn.remove(team);
        }
    }
    public boolean isCore(Block block){
        return cores.containsValue(block);
    }
    public teamColors getCore(Block block){
        for(Map.Entry<teamColors, Block> data : this.cores.entrySet()){
            if(data.getValue().equals(block)){
                return data.getKey();
            }
        }
        return null;
    }
    public Block getCore(teamColors team){
        return this.cores.get(team);
    }

    public Block getTeamChest(teamColors team) { return this.team_chests.get(team); }
    public teamColors getTeamChest(Block block) {
        for(Map.Entry<teamColors, Block> data : this.team_chests.entrySet()){
            if(data.getValue().equals(block)){
                return data.getKey();
            }
        }
        return null;
    }

    public void addCore(teamColors team, Block core){
        cores.put(team,core);
    }
    public boolean getCanRespawn(teamColors team) {
        return !cantRespawn.contains(team);
    }
    public String getTeamColor(teamColors team){
        return teamColors.valueOf(team.toString().toUpperCase()).getChatColor();
    }

    public void doEliminatePlayer(teamColors team, Player player) {
        this.team_players.get(team).remove(player);
        if(this.getRemainingPlayers(team)<=0){ this.doEliminateTeam(team); }
    }
    public int getRemainingPlayers(teamColors team) { return this.team_players.get(team).size(); }

    public void doEliminateTeam(teamColors team) {
        Bukkit.broadcastMessage(Text.format(String.format("%s&7 team have been eliminated!",getTeamColor(team)+team)));
        this.active_teams.remove(team);
        if(this.getActive_teams().size()==1){
            game.stop(true);
        }
    }

    public void setScoreboardLine (teamColors team, int id) {this.scoreboard_lines.put(team,id);}
    public int getScoreboardLine (teamColors team) {return this.scoreboard_lines.get(team);}

    public void assignTeams(){
        teamColors team;
        int iterator = 0;

        ArrayList<Player> WHITE_PLAYERS = new ArrayList<>();
        ArrayList<Player> PURPLE_PLAYERS = new ArrayList<>();
        ArrayList<Player> RED_PLAYERS = new ArrayList<>();
        ArrayList<Player> BLUE_PLAYERS = new ArrayList<>();
        ArrayList<Player> GREEN_PLAYERS = new ArrayList<>();
        ArrayList<Player> YELLOW_PLAYERS = new ArrayList<>();
        ArrayList<Player> ORANGE_PLAYERS = new ArrayList<>();
        ArrayList<Player> GREY_PLAYERS = new ArrayList<>();

        for(Player p : Bukkit.getOnlinePlayers()){
            if(iterator==0){
                // BLUE TEAM
                BLUE_PLAYERS.add(p);
                players.put(p,teamColors.BLUE);
                p.setDisplayName(getTeamColor(teamColors.BLUE)+p.getName());
                if(!(this.active_teams.contains(teamColors.BLUE))){this.active_teams.add(teamColors.BLUE);}
                team=teamColors.BLUE;
                iterator++;
            }else if(iterator==1){
                // ORANGE TEAM
                ORANGE_PLAYERS.add(p);
                players.put(p,teamColors.ORANGE);
                p.setDisplayName(getTeamColor(teamColors.ORANGE)+p.getName());
                if(!(this.active_teams.contains(teamColors.ORANGE))){this.active_teams.add(teamColors.ORANGE);}
                team=teamColors.ORANGE;
                iterator++;
            }else if(iterator==2){
                // RED TEAM
                RED_PLAYERS.add(p);
                players.put(p,teamColors.RED);
                p.setDisplayName(getTeamColor(teamColors.RED)+p.getName());
                if(!(this.active_teams.contains(teamColors.RED))){this.active_teams.add(teamColors.RED);}
                team=teamColors.RED;
                iterator++;
            }else if(iterator==3){
                // WHITE TEAM
                WHITE_PLAYERS.add(p);
                players.put(p,teamColors.WHITE);
                p.setDisplayName(getTeamColor(teamColors.WHITE)+p.getName());
                if(!(this.active_teams.contains(teamColors.WHITE))){this.active_teams.add(teamColors.WHITE);}
                team=teamColors.WHITE;
                iterator++;
            }else if(iterator==4){
                // YELLOW TEAM
                YELLOW_PLAYERS.add(p);
                players.put(p,teamColors.YELLOW);
                p.setDisplayName(getTeamColor(teamColors.YELLOW)+p.getName());
                if(!(this.active_teams.contains(teamColors.YELLOW))){this.active_teams.add(teamColors.YELLOW);}
                team=teamColors.YELLOW;
                iterator++;
            }else if(iterator==5){
                // PURPLE TEAM
                PURPLE_PLAYERS.add(p);
                players.put(p,teamColors.PURPLE);
                p.setDisplayName(getTeamColor(teamColors.PURPLE)+p.getName());
                if(!(this.active_teams.contains(teamColors.PURPLE))){this.active_teams.add(teamColors.PURPLE);}
                team=teamColors.PURPLE;
                iterator++;
            }else if(iterator==6){
                // GREY TEAM
                GREY_PLAYERS.add(p);
                players.put(p,teamColors.GREY);
                p.setDisplayName(getTeamColor(teamColors.GREY)+p.getName());
                if(!(this.active_teams.contains(teamColors.GREY))){this.active_teams.add(teamColors.GREY);}
                team=teamColors.GREY;
                iterator++;
            }else {
                // GREEN TEAM
                GREEN_PLAYERS.add(p);
                players.put(p,teamColors.GREEN);
                p.setDisplayName(getTeamColor(teamColors.GREEN)+p.getName());
                if(!(this.active_teams.contains(teamColors.GREEN))){this.active_teams.add(teamColors.GREEN);}
                team=teamColors.GREEN;
                iterator=0;
            }
            p.setPlayerListName(Text.format(teamColors.valueOf(team.toString().toUpperCase()).getChatColor()+p.getName()));
            p.sendMessage(Text.format(String.format(lang.GAME_TEAM_ASSIGNED.get(), teamColors.valueOf(team.toString().toUpperCase()).getChatColor()+team.toString().toUpperCase())));
        }
        if(BLUE_PLAYERS.size()>0){this.team_players.put(teamColors.BLUE,BLUE_PLAYERS);}
        if(ORANGE_PLAYERS.size()>0){this.team_players.put(teamColors.ORANGE,ORANGE_PLAYERS);}
        if(RED_PLAYERS.size()>0){this.team_players.put(teamColors.RED,RED_PLAYERS);}
        if(WHITE_PLAYERS.size()>0){this.team_players.put(teamColors.WHITE,WHITE_PLAYERS);}
        if(YELLOW_PLAYERS.size()>0){this.team_players.put(teamColors.YELLOW,YELLOW_PLAYERS);}
        if(PURPLE_PLAYERS.size()>0){this.team_players.put(teamColors.PURPLE,PURPLE_PLAYERS);}
        if(GREY_PLAYERS.size()>0){this.team_players.put(teamColors.GREY,GREY_PLAYERS);}
        if(GREEN_PLAYERS.size()>0){this.team_players.put(teamColors.GREEN,GREEN_PLAYERS);}

    }

    public HashMap<Player, teamColors> getPlayers() {
        return players;
    }
}
