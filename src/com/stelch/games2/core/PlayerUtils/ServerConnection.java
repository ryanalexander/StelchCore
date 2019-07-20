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
 *  * @since 19/7/2019
 *  
 */

package com.stelch.games2.core.PlayerUtils;

import com.stelch.games2.core.BungeeCore;
import com.stelch.games2.core.Utils.Text;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ServerConnection {
    
    public static ServerInfo sendToHub(){
        ArrayList<ServerInfo> hubs = new ArrayList<>();
        for(BungeeCore.GameServer gameServer : BungeeCore.servers.values()){
            ServerInfo server = gameServer.build();
            System.out.println(String.format("Found %s : ishub? [%s]",server.getName(),server.getName().toLowerCase().startsWith("hub")));
            if(server.getName().toLowerCase().startsWith("hub")){
                hubs.add(server);
            }
        }
        int rand = ThreadLocalRandom.current().nextInt(0, hubs.size());
        return hubs.get(rand);
    }
    
}
