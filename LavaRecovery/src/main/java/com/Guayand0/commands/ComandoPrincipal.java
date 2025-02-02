package com.Guayand0.commands;

import com.Guayand0.LavaRecovery;
import com.Guayand0.managers.LanguageManager;
import com.Guayand0.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ComandoPrincipal implements CommandExecutor {

    private final LavaRecovery plugin;
    private final LanguageManager languageManager;
    private final MessageUtils MU = new MessageUtils();

    public ComandoPrincipal(LavaRecovery plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            // Consola
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    String messagePath = "messages.reload";
                    String message = languageManager.getMessage(messagePath);
                    sender.sendMessage(MU.getColoredReplacePluginPlaceholdersText(message, plugin.replacePluginPlaceholders));
                    return true;
                }
                String messagePath = "messages.console-error";
                String message = languageManager.getMessage(messagePath);
                sender.sendMessage(MU.getColoredReplacePluginPlaceholdersText(message, plugin.replacePluginPlaceholders));
                return true;
            }
            String messagePath = "messages.console-error";
            String message = languageManager.getMessage(messagePath);
            sender.sendMessage(MU.getColoredReplacePluginPlaceholdersText(message, plugin.replacePluginPlaceholders));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lavarecovery.admin")) {
            noPerm(player);
            return true;
        }

        // /lavarecovery args[0] args[1] args[2]
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                // lavarecovery reload
                subCommandReload(player);
            } else if (args[0].equalsIgnoreCase("help")) {
                // lavarecovery help
                help(player);
            } else if (args[0].equalsIgnoreCase("version")) {
                // lavarecovery version
                subCommandVersion(player);
            } else if (args[0].equalsIgnoreCase("author")) {
                // lavarecovery author
                subCommandAutor(player);
            }else if (args[0].equalsIgnoreCase("plugin")) {
                // lavarecovery plugin
                subCommandPlugin(player);
            } else if (args[0].equalsIgnoreCase("permissions")) {
                // lavarecovery permissions
                subCommandPermissions(player);
            } else {
                noArg(player); // lavarecovery qwewe
            }
        } else {
            noArg(player); // lavarecovery
        }
        return true;
    }

    public void help(Player player) {
        String messagePath = "messages.help";
        List<String> fullMsg = languageManager.getStringList(messagePath);
        for (String message : fullMsg) {
            player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
        }
    }

    public void subCommandReload(Player player) {
        plugin.reloadConfig();
        plugin.registerPlaceholders();
        languageManager.reloadLanguage();

        String messagePath = "messages.reload";
        String message = languageManager.getMessage(messagePath);
        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
    }

    public void subCommandVersion(Player player) {
        String messagePath = "messages.version";
        String message = languageManager.getMessage(messagePath);
        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
    }

    public void subCommandAutor(Player player) {
        String messagePath = "messages.author";
        String message = languageManager.getMessage(messagePath);
        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
    }

    public void subCommandPlugin(Player player){
        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, "%plugin% &7%link%", plugin.replacePluginPlaceholders));
    }

    public void subCommandPermissions(Player player){
        String messagePath = "messages.permissions";
        List<String> fullMsg = languageManager.getStringList(messagePath);
        for (String message : fullMsg) {
            player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
        }
    }

    public void noPerm(Player player){
        String messagePath = "messages.no-perm";
        String message = languageManager.getMessage(messagePath);
        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
    }

    public void noArg(Player player){
        String messagePath = "messages.command-no-argument";
        String message = languageManager.getMessage(messagePath);
        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
    }

    public void consoleError(Player player){
        String messagePath = "messages.console-error";
        String message = languageManager.getMessage(messagePath);
        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
    }
}