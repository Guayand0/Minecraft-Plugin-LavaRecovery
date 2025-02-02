package com.Guayand0.utils;

import com.Guayand0.LavaRecovery;
import com.Guayand0.managers.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.List;

public class CheckForUpdates implements Listener {

    private final LavaRecovery plugin;
    private final LanguageManager languageManager;

    private final UpdateChecker UC = new UpdateChecker();
    private final MessageUtils MU = new MessageUtils();

    public CheckForUpdates(LavaRecovery plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @EventHandler
    public void checkUpdate(PlayerJoinEvent event) {
        try{
            Player player = event.getPlayer();
            boolean updateChecker = plugin.getConfig().getBoolean("config.update-checker");

            // Verificar si el mensaje de actualización debe enviarse
            if (updateChecker && (UC.compareVersions(plugin.currentVersion, plugin.lastVersion) < 0)) {

                if (player.isOp() || player.hasPermission("lavarecovery.updatechecker") || player.hasPermission("lavarecovery.admin")) {

                    if (!plugin.updateCheckerWork) {
                        plugin.comprobarActualizaciones();
                    }

                    String messagePath = "config.update-checker";
                    List<String> fullMsg = languageManager.getStringList(messagePath);
                    for (String message : fullMsg) {
                        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
                    }
                }
            }
        }catch (NullPointerException e){
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
            e.printStackTrace();
        }
    }
}
