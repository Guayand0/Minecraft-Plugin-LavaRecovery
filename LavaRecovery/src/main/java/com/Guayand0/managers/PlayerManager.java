package com.Guayand0.managers;

import com.Guayand0.LavaRecovery;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PlayerManager {

    private final LavaRecovery plugin;
    private File playerFile;
    private FileConfiguration playerConfig;

    public PlayerManager(LavaRecovery plugin) {
        this.plugin = plugin;
        initializePlayerFile();
    }

    // Inicializa el archivo de jugador, copia el archivo por defecto si no existe
    private void initializePlayerFile() {
        playerFile = new File(plugin.getDataFolder(), "player.yml");
        if (!playerFile.exists()) {
            // Copia el archivo por defecto desde los recursos del plugin
            plugin.saveResource("player.yml", false);
        }
        reloadPlayer(); // Carga la configuración
    }

    // Devuelve la configuración actual del jugador
    public FileConfiguration getPlayer() {
        return playerConfig;
    }

    // Guarda la configuración actual en el archivo
    public void savePlayer() {
        if (playerConfig == null) {
            return;
        }
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recarga la configuración del jugador desde el archivo
    public void reloadPlayer() {
        if (playerFile == null) {
            return;
        }

        // Carga el archivo de configuración
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        // Cargar cualquier configuración predeterminada si existe
        InputStream defConfigStream = plugin.getResource("player.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            playerConfig.setDefaults(defConfig);
        }
    }
}