package com.ruserious99.network;

import com.ruserious99.network.commands.FruitCommand;
import com.ruserious99.network.commands.PingCommand;
import com.ruserious99.network.commands.PlayerMessageCommand;
import com.ruserious99.network.commands.ReplyMessageCommand;
import com.ruserious99.network.commands.listener.LeakPrevention;
import com.ruserious99.network.events.PostLogin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public final class Network extends Plugin implements Listener {

    private Favicon favicon;
    private HashMap<UUID, UUID> recentMessages;

    @Override
    public void onEnable() {

        recentMessages = new HashMap<>();

        try {
            favicon = Favicon.create(ImageIO.read(new File("favicon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerEvents();
        registerCommands();
        // runSchedulers();
    }

    private void runSchedulers() {
        ProxyServer.getInstance().getScheduler().schedule(this, () -> {
            System.out.println("Scheduler is working");
        }, 10, 5, TimeUnit.SECONDS);
    }

    private void registerEvents() {
        getProxy().getPluginManager().registerListener(this, this);
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PostLogin());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new LeakPrevention(this));

    }

    private void registerCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReplyMessageCommand(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PlayerMessageCommand(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PingCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new FruitCommand());
    }


    //Events
    @EventHandler
    public void onPing(ProxyPingEvent pingEvent) {

        ServerPing ping = pingEvent.getResponse();
        ping.setDescription(ChatColor.DARK_PURPLE + "MysticWorld\n"
                + ChatColor.YELLOW + " "
                + ChatColor.BOLD + "Come Enjoy, "
                + ChatColor.BLUE + "A Chill Server to Play on");
        ping.setPlayers(new ServerPing.Players(1000, getProxy().getOnlineCount(), ping.getPlayers().getSample()));
        ping.setVersion(new ServerPing.Protocol("Best Connect with 1.18", 757));
        ping.setFavicon(favicon);

        pingEvent.setResponse(ping);
    }

    //Getters
    public HashMap<UUID, UUID> getRecentMessages() {return recentMessages;}
}
