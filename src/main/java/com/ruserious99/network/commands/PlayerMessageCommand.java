package com.ruserious99.network.commands;

import com.ruserious99.network.Network;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerMessageCommand extends Command implements TabExecutor {

    private Network network;

    public PlayerMessageCommand(Network network) {
        super("bmessage");
        this.network = network;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {


        if (sender instanceof ProxiedPlayer) {

            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (args.length >= 2) {
                ProxiedPlayer playerTarget = ProxyServer.getInstance().getPlayer(args[0]);
                if (playerTarget != null) {
                    StringBuilder message = new StringBuilder();
                    for (String s : args) {
                        if (!s.equals(args[1])) {
                            message.append(" ").append(s);
                        }
                    }
                    player.sendMessage("you sent -> " + playerTarget.getDisplayName() + ": " + message);
                    playerTarget.sendMessage(ChatColor.DARK_GREEN + player.getDisplayName() + " sent you -> " + message);

                    network.getRecentMessages().put(player.getUniqueId(), playerTarget.getUniqueId());
                } else {
                    player.sendMessage("Player not found");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: bmessage <player> <message>");
            }
        }
    }

        @Override
        public Iterable<String> onTabComplete (CommandSender sender, String[]args){

            List<String> results = new ArrayList<>();

            if (args.length == 1) {
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    results.add(player.getName());
                    return results.stream().filter(value -> value.toLowerCase()
                            .startsWith(args[0].toLowerCase())).collect(Collectors.toList());
                }
            }
            return results;
        }
    }
