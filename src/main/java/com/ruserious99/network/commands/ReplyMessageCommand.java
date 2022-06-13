package com.ruserious99.network.commands;

import com.ruserious99.network.Network;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyMessageCommand extends Command {

    private final Network network;

    public ReplyMessageCommand(Network network) {
        super("reply");
        this.network = network;
    }

    //     /reply <message>

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (args.length >= 1) {
                if (network.getRecentMessages().containsKey(player.getUniqueId())) {
                    ProxiedPlayer playerTarget = ProxyServer.getInstance().getPlayer(network.getRecentMessages().get(player.getUniqueId()));
                    if (playerTarget != null) {
                        StringBuilder message = new StringBuilder();
                        for (String s : args) {
                            message.append(" ").append(s);
                        }
                        player.sendMessage("you sent -> " + playerTarget.getDisplayName() + ": " + message);
                        playerTarget.sendMessage(ChatColor.DARK_GREEN + player.getDisplayName() + " sent you -> " + message);

                        network.getRecentMessages().put(player.getUniqueId(), playerTarget.getUniqueId());
                    } else {
                        network.getRecentMessages().remove(player.getUniqueId());
                        player.sendMessage("Player went off work");
                    }
                } else {
                    player.sendMessage("You have not messaged anyone recently.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /reply <message>");

            }
        }
    }
}
