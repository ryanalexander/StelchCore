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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityManager {
    private Game game;

    public EntityManager(Game game){this.game=game;}

    private ArrayList<Entity> gameEntities = new ArrayList<>();

    private HashMap<Entity, click> EntityFunctions = new HashMap<>();

    public boolean GameEntity(Entity e){return gameEntities.contains(e);}

    public void remove(Entity e){
        if(hasFunction(e))EntityFunctions.remove(e);
        if(GameEntity(e))gameEntities.remove(e);
        e.remove();
    }

    public boolean hasFunction(Entity e){return EntityFunctions.containsKey(e);}
    public click EntityFunction(Entity e){return EntityFunctions.get(e);}

    public static interface click {
        void run(Player param1Player);
    }
}