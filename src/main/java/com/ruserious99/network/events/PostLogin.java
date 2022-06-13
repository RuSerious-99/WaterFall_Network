package com.ruserious99.network.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLogin implements Listener {
    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        e.getPlayer().sendMessage(ChatColor.BLUE + "Welcome " + e.getPlayer().getDisplayName() + " Enjoy your Stay");
    }
}