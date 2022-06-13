package com.ruserious99.network.commands.listener;

import com.ruserious99.network.Network;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LeakPrevention implements Listener {

    private final Network network;

    public LeakPrevention(Network network) {
        this.network = network;
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e){

        if(network.getRecentMessages().containsKey(e.getPlayer().getUniqueId())){
            network.getRecentMessages().remove(e.getPlayer().getUniqueId());
        }
    }
}
