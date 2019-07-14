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

package com.stelch.games2.core.Game;

import com.stelch.games2.core.API;
import com.stelch.games2.core.Commands.bukkit.game;
import com.stelch.games2.core.Commands.bukkit.mct;
import com.stelch.games2.core.Events.bukkit.Game.*;
import com.stelch.games2.core.Game.Utils.Functions.start;
import com.stelch.games2.core.Game.Utils.Functions.stop;
import com.stelch.games2.core.Game.Utils.Managers.BlockManager;
import com.stelch.games2.core.Game.Utils.Managers.EntityManager;
import com.stelch.games2.core.Game.Utils.Managers.SpectatorManager;
import com.stelch.games2.core.Game.Utils.Managers.TeamManager;
import com.stelch.games2.core.Game.varables.gameState;
import com.stelch.games2.core.Game.varables.gameType;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Game {

    // Game Title
    private String title;

    // Game Type
    private gameType gameType;

    // TeamManager instance
    private TeamManager teamManager;

    // Minimum players for game to start (Naturally)
    private int min_players;

    // Max players the game will allow
    private int max_players;

    // Handler for game
    private JavaPlugin handler;

    // Block Manager
    private BlockManager blockManager;

    // EntityManager
    private EntityManager entityManager;

    // SpectatorManager
    private SpectatorManager spectatorManager;

    // Default map for game
    private World map;

    // Current gameState
    private gameState gameState;

    // Should the game autostart
    private boolean AutoStart;

    /**
     *
     * @param title What will the game be called?
     * @param gameType Game type from gameType enum
     * @param min_players Minimum players for game to start (Naturally)
     * @param max_players Max players the game will allow
     * @param handler Handler for game
     * @param map Default map for game
     * @since 14/07/2019
     *
     */
    public Game(String title, gameType gameType, int min_players, int max_players, JavaPlugin handler, World map){
        this.title=title;
        this.gameType=gameType;
        this.min_players=min_players;
        this.max_players=max_players;
        this.handler=handler;
        this.map=map;
        this.spectatorManager=new SpectatorManager(this);
        this.entityManager=new EntityManager(this);
        this.teamManager=new TeamManager(this);
        this.blockManager =new BlockManager(this);
        this.gameState=gameState.LOBBY;
        this.AutoStart=true;
        handler.getCommand("mct").setExecutor(new mct(handler,this));
        handler.getCommand("game").setExecutor(new game(handler,this));
        this.apiPush();
    }

    /**
     * @return are to minimum requirements met for game to start
     * @since 14/07/2019
     */
    public boolean canStart() {
        boolean start=true;
        if(Bukkit.getOnlinePlayers().size()<this.min_players){start=false;}
        if(!this.AutoStart){start=false;}
        return start;
    }

    /**
     *
     * @return Game type from gameType enum
     * @since 14/07/2019
     */
    public gameType GameType() {
        return gameType;
    }

    /**
     *
     * @return Max players the game will allow
     * @since 14/07/2019
     */
    public int maxPlayers() {
        return max_players;
    }

    /**
     *
     * @param max_players Max players the game will allow
     * @since 14/07/2019
     */
    public void maxPlayers(int max_players) {
        this.max_players = max_players;
    }

    /**
     *
     * @return Minimum players for game to start (Naturally)
     * @since 14/07/2019
     */
    public int minPlayers() {
        return min_players;
    }

    /**
     *
     * @param min_players Minimum players for game to start (Naturally)
     * @since 14/07/2019
     */
    public void minPlayers(int min_players) {
        this.min_players = min_players;
    }
    /**
     *
     * @return Handler for game
     * @since 14/07/2019
     */
    public Plugin handler() {
        return handler;
    }

    /**
     *
     * @return What will the game be called?
     * @since 14/07/2019
     */
    public String title() {
        return title;
    }

    /**
     *
     * @param title What will the game be called?
     * @since 14/07/2019
     */
    public void title(String title) {
        this.title = title;
    }
    /**
     *
     * @param gameType Game type from gameType enum
     * @since 14/07/2019
     */
    public void GameType(gameType gameType) {
        this.gameType = gameType;
    }

    /**
     *
     * @param map Set current map for game
     * @since 14/07/2019
     */
    public void map(World map) {
        this.map = map;
    }

    /**
     *
     * @return Default map for game
     * @since 14/07/2019
     */
    public World map() {
        return map;
    }

    /**
     *
     * @return fetch current GameState
     * @since 14/07/2019
     */
    public gameState GameState() {
        return this.gameState;
    }

    /**
     *
     * @param gameState from gameState enum, which gameState shall be set
     * @see gameState for options
     * @since 14/07/2019
     */
    public void GameState(gameState gameState) {
        this.apiPush();
        this.gameState = gameState;
    }

    /**
     *
     * @return will the game autostart when requirements are met
     * @since 14/07/2019
     */
    public boolean AutoStart() {
        return AutoStart;
    }

    /**
     *
     * @param auto_start should the game autostart when requirements are met
     * @since 14/07/2019
     */
    public void AutoStart(boolean auto_start) {
        this.AutoStart = auto_start;
    }

    /**
     *
     * @return Game instance of TeamManager
     */
    public TeamManager TeamManager() {
        return teamManager;
    }

    /**
     *
     * @return Game instance of EntityManager
     */
    public EntityManager EntityManager() {
        return entityManager;
    }

    /**
     *
     * @return Game instance of SpectatorManager
     */
    public SpectatorManager SpectatorManager() {
        return spectatorManager;
    }

    /**
     *
     * @return BlockManager instance
     * @since 14/07/2019
     */
    public BlockManager BlockManager() {
        return this.blockManager;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    // Main game start function
    public void start() {new start(this);}

    // Main gamer stop function
    public void stop(boolean isNatural){new stop(this,isNatural);}

    // Register required events
    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new playerJoin(this),handler);
        pm.registerEvents(new blockPlace(this),handler);
        pm.registerEvents(new AsyncChatEvent(this),handler);
        pm.registerEvents(new playerDeathEvent(this),handler);
        pm.registerEvents(new CommandPreProcessEvent(this),handler);
        pm.registerEvents(new EntityInteract(this),handler);
        pm.registerEvents(new PlayerMoveEvent(this),handler);
    }

    private void apiPush() {
        API.setGame(this.title);
        API.setState(this.gameState.name());
    }


}
