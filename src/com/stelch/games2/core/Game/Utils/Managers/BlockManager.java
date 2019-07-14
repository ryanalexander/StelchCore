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

package com.stelch.games2.core.Game.Utils.Managers;

import com.stelch.games2.core.Game.Game;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockManager {
    private Game game;

    public BlockManager(Game game){this.game=game;}


    private HashMap<Location, Block> changes;

    /**
     * Block Handler
     */
    public BlockManager(){changes=new HashMap<>();}

    /**
     * Can player break specific block?
     * @param block specific block in world
     */
    public boolean canBreakBlock(Block block){ return !this.changes.containsKey(block.getLocation()); }

    /**
     *
     * @param location Block location
     * @param block clone of original block
     */
    public void update(Location location, Block block){
        if (this.changes.containsKey(location)) return;
        this.changes.put(location,block);
    }

    public void doRollback() {
        for(Map.Entry<Location, Block> entry : changes.entrySet()){
            entry.getKey().getBlock().setType(entry.getValue().getType());
            entry.getKey().getBlock().setBlockData(entry.getValue().getBlockData());
        }
    }

}
