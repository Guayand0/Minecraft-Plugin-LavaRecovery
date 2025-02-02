package com.Guayand0.events;

import com.Guayand0.LavaRecovery;
import com.Guayand0.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class OnPlayerJoin implements Listener {

    private final LavaRecovery plugin;
    private final PlayerManager playerManager;

    public OnPlayerJoin(LavaRecovery plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerManager.reloadPlayer();

        // Obtener una nueva instancia de playerConfig
        FileConfiguration playerConfig = playerManager.getPlayer();

        Player player = event.getPlayer();
        String playerPath = "player." + player.getUniqueId() + "." + player.getName();
        List<String> allowedWorlds = plugin.getConfig().getStringList("config.allowed-worlds");

        // Si el jugador no tiene el campo "recovery-times" por cada mundo, asignar 0 por defecto
        for (String worldName : allowedWorlds) {
            String worldPath = playerPath + ".allowed-worlds-name." + worldName + ".recovery-times";

            // Verificar si no existe el campo para ese mundo y asignar 0
            if (!playerConfig.contains(worldPath)) {
                playerConfig.set(worldPath, 0);
            }
        }

        // Guardar la configuración del banco después de las asignaciones
        playerManager.savePlayer();
    }
}