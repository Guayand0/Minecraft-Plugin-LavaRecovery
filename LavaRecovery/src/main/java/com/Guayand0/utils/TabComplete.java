package com.Guayand0.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // Verifica si el sender tiene el permiso "lavarecovery.admin"
        boolean hasAdminPermission = sender.hasPermission("lavarecovery.admin");

        if (command.getName().equalsIgnoreCase("lavarecovery") || command.getName().equalsIgnoreCase("lr")) {
            if (args.length == 1) {
                if (hasAdminPermission) {
                    completions.addAll(Arrays.asList("help", "reload", "version", "author", "permissions", "plugin"));
                }
            }
        }
        return completions.stream().filter(option -> option.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
    }
}