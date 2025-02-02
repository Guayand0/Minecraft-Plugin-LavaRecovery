package com.Guayand0;

import com.Guayand0.commands.*;
import com.Guayand0.events.OnPlayerJoin;
import com.Guayand0.events.PlayerInteract;
import com.Guayand0.managers.*;
import com.Guayand0.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LavaRecovery extends JavaPlugin {

    public String prefix = "&4[&6&l" + getDescription().getName() + "&4]&f";
    public String author = getDescription().getAuthors().toString();
    public String currentVersion = getDescription().getVersion();
    public String lastVersion;
    public boolean updateCheckerWork = true;
    public boolean PlaceholderAPIEnable = false;
    public Map<String, String> replacePluginPlaceholders = new HashMap<>();

    public static int spigotID = 122322;
    public static int bstatsID = 24611;

    private final MessageUtils MU = new MessageUtils();
    private final UpdateChecker UC = new UpdateChecker();
    private LanguageManager languageManager;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        try {
            saveDefaultConfig();
            getLastVersion();
            registerPlaceholders();

            languageManager = new LanguageManager(LavaRecovery.this);
            playerManager = new PlayerManager(LavaRecovery.this);

            registrarComandos();
            registrarEventos();

            // Usar variables PlaceholderAPI
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                PlaceholderAPIEnable = true;
                Bukkit.getConsoleSender().sendMessage(MU.getColoredText("&9<------------------------------------>"));
                Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &fPlaceholderAPI detected."));
            }

            // Ejecutar comprobarActualizaciones() después de que el servidor haya iniciado completamente
            new BukkitRunnable() {
                @Override
                public void run() {
                    comprobarActualizaciones();
                }
            }.runTask(this); // Ejecuta la tarea en el siguiente tick

        Metrics metrics = new Metrics(this, bstatsID); // Bstats

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &cError while enabling plugin."));
            e.printStackTrace();
        }

        Bukkit.getConsoleSender().sendMessage(MU.getColoredText("&9<------------------------------------>"));
        Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &fEnabled, (&aVersion: &e" + currentVersion + "&f)"));
        Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &bThanks for using my plugin :)"));
        Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &eMade by &dGuayand0"));
        Bukkit.getConsoleSender().sendMessage(MU.getColoredText("&9<------------------------------------>"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &fDisabled, (&aVersion: &b" + currentVersion + "&f)"));
        Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &eBye :)"));
    }

    private void registrarComandos() {
        this.getCommand("lavarecovery").setExecutor(new ComandoPrincipal(this));
        // TabComplete
        this.getCommand("lavarecovery").setTabCompleter(new TabComplete());
    }

    private void registrarEventos() {
        getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);
        getServer().getPluginManager().registerEvents(new CheckForUpdates(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);
    }

    public void registerPlaceholders() {
        replacePluginPlaceholders.put("%plugin%", prefix);
        replacePluginPlaceholders.put("%chatplugin%", getConfig().getString("config.chat-prefix", "&4[&6&l" + getDescription().getName() + "&4]&f"));
        replacePluginPlaceholders.put("%version%", currentVersion);
        replacePluginPlaceholders.put("%latestversion%", lastVersion);
        replacePluginPlaceholders.put("%link%", "https://www.spigotmc.org/resources/" + spigotID);
        replacePluginPlaceholders.put("%author%", author);
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void getLastVersion() {
        try {
            lastVersion = UC.getLatestSpigotVersion(spigotID, 5000);  // Obtener la última versión desde la clase UpdateChecker
        } catch (SocketTimeoutException ex) {
            Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &cConection timed out. The version will be checked later."));
            lastVersion = currentVersion;
            updateCheckerWork = false;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &cError while checking update."));
            lastVersion = currentVersion;
            updateCheckerWork = false;
        }
    }

    // Metodo para comprobar ultima version
    public void comprobarActualizaciones() {
        if (UC.compareVersions(currentVersion, lastVersion) < 0) {
            String messagePath = "config.update-checker";
            List<String> fullMsg = languageManager.getStringList(messagePath);
            for (String message : fullMsg) {
                Bukkit.getConsoleSender().sendMessage(MU.getCheckAllPlaceholdersText(this.getPlaceholderAPI(), null, message, this.replacePluginPlaceholders));
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(MU.getColoredText(prefix + " &aYou are using the last version. &f(&b" + currentVersion + "&f)"));
        }
        updateCheckerWork = true;
    }

    // Comprobar si tiene PlaceholderAPI activado
    public boolean getPlaceholderAPI(){
        return PlaceholderAPIEnable;
    }
}
