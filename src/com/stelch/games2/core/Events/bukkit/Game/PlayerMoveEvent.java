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
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class PlayerMoveEvent implements Listener {

    Game game;
    public PlayerMoveEvent(Game game){this.game=game;}

    @EventHandler
    public void onMove(org.bukkit.event.player.PlayerMoveEvent e) {/*
        if (Main.game.invis_players.containsKey(e.getPlayer())) {
            Main.game.invis_players.get(e.getPlayer()).teleport(e.getPlayer());
            Location loc = e.getPlayer().getLocation();
            e.getTo().getWorld().spawnParticle(Particle.DRAGON_BREATH, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), Float.valueOf(loc.getYaw() + ""), Float.valueOf(loc.getPitch() + "")), 1, 0, 0, 0, 0);
        }*/
    }
}
