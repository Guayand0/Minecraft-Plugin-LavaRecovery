package com.Guayand0.events;

import com.Guayand0.LavaRecovery;
import com.Guayand0.managers.LanguageManager;
import com.Guayand0.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.HashMap;
import java.util.UUID;

public class PlayerInteract implements Listener {

    private final LavaRecovery plugin;
    private final LanguageManager languageManager;
    private final HashMap<UUID, Long> cooldowns;

    private final MessageUtils MU = new MessageUtils();

    public PlayerInteract(LavaRecovery plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
        this.cooldowns = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        boolean obsidiantolava = plugin.getConfig().getBoolean("config.obsidian-to-lava", true);
        List<String> allowedWorlds = plugin.getConfig().getStringList("config.allowed-worlds");
        boolean cooldownEnabled = plugin.getConfig().getBoolean("config.cooldown.enable", true);
        int cooldownTime = plugin.getConfig().getInt("config.cooldown.time", 60);

        if (!obsidiantolava) {
            // Si obsidian-to-lava es false, no hacer nada
            return;
        }

        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();

        if (!allowedWorlds.contains(worldName)) {
            // Si el mundo no está en allowed-worlds, no hacer nada
            return;
        }

        if (!player.hasPermission("lavarecovery.use")) {
            // Si el jugador no tiene el permiso, no hacer nada
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {

            ItemStack itemInHand = event.getItem();
            if (itemInHand == null || itemInHand.getType() != Material.BUCKET) {
                // Si en la mano no hay cubo vacio, no hacer nada
                return;
            }

            Block block = event.getClickedBlock();
            if (block == null || block.getType() != Material.OBSIDIAN) {
                // Si el bloque clickado no es obsidiana, no hacer nada
                return;
            }

            if (cooldownEnabled && !player.hasPermission("lavarecovery.cooldown.exempt")) {
                UUID playerUUID = player.getUniqueId();
                long currentTime = System.currentTimeMillis();
                if (cooldowns.containsKey(playerUUID)) {
                    long lastUseTime = cooldowns.get(playerUUID);
                    long timeRemaining = (cooldownTime * 1000) - (currentTime - lastUseTime);
                    if (timeRemaining > 0) {
                        String messagePath = "config.cooldown-message";
                        String message = languageManager.getMessage(messagePath);
                        if (message != null) {
                            plugin.replacePluginPlaceholders.put("%timereaming%", String.valueOf(timeRemaining / 1000));
                            player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
                        }
                        return;
                    }
                }
                // Actualizar el tiempo de la última interacción
                cooldowns.put(playerUUID, currentTime);
            }

            Inventory inventory = player.getInventory();

            if (itemInHand.getAmount() == 1) {
                // Si el jugador tiene solo un cubo en la mano, convertirlo en un cubo de lava
                itemInHand.setType(Material.LAVA_BUCKET);
            } else {
                // Si el jugador tiene varios cubos, disminuir la cantidad y añadir un cubo de lava al inventario
                itemInHand.setAmount(itemInHand.getAmount() - 1);
                ItemStack lavaBucket = new ItemStack(Material.LAVA_BUCKET);

                // Añadir cubo de lava al inventario, o tirarlo al suelo si el inventario está lleno
                if (inventory.addItem(lavaBucket).size() != 0) {
                    // Inventario lleno, tirar al suelo
                    player.getWorld().dropItemNaturally(player.getLocation(), lavaBucket);
                }
            }

            // Cambiar el bloque de obsidiana a aire
            block.setType(Material.AIR);
        }
    }
}

/*package com.Guayand0.events;

import com.Guayand0.GYOlib.MessageUtils;
import com.Guayand0.LavaRecovery;
import com.Guayand0.managers.LanguageManager;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.HashMap;
import java.util.UUID;

public class PlayerInteract implements Listener {

    private final LavaRecovery plugin;
    private final LanguageManager languageManager;
    private final HashMap<UUID, Long> cooldowns;

    private final MessageUtils MU = new MessageUtils();

    public PlayerInteract(LavaRecovery plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
        this.cooldowns = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        boolean obsidiantolava = plugin.getConfig().getBoolean("config.obsidian-to-lava", true);
        List<String> allowedWorlds = plugin.getConfig().getStringList("config.allowed-worlds");
        boolean cooldownEnabled = plugin.getConfig().getBoolean("config.cooldown.enable", true);
        int cooldownTime = plugin.getConfig().getInt("config.cooldown.time");

        if (!obsidiantolava) {
            // Si obsidian-to-lava es false, no hacer nada
            return;
        }

        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();

        if (!allowedWorlds.contains(worldName)) {
            // Si el mundo no está en allowed-worlds, no hacer nada
            return;
        }

        if (!player.isOp() || !player.hasPermission("lavarecovery.use") || !player.hasPermission("lavarecovery.admin")) {
            // Si el jugador no tiene el permiso, no hacer nada
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            player.sendMessage(MU.getColoredText("%a[CLICK IZQUIERDO]"));
            Block block = event.getClickedBlock();
            if (block != null && block.getType() == Material.OBSIDIAN) {
                ItemStack itemInHand = event.getItem();
                if (itemInHand != null && itemInHand.getType() == Material.BUCKET) {

                    if (cooldownEnabled && !player.hasPermission("lavarecovery.cooldown.exempt")) {
                        UUID playerUUID = player.getUniqueId();
                        long currentTime = System.currentTimeMillis();
                        if (cooldowns.containsKey(playerUUID)) {
                            long lastUseTime = cooldowns.get(playerUUID);
                            long timeRemaining = (cooldownTime * 1000) - (currentTime - lastUseTime);
                            if (timeRemaining > 0) {
                                String messagePath = "config.cooldown-message";
                                String message = languageManager.getMessage(messagePath);
                                if (message != null) {
                                    message = message.replaceAll("%plugin%", plugin.prefix).replace("%timereaming%", String.valueOf(timeRemaining / 1000));
                                    player.sendMessage(MU.getColoredText(message));
                                }
                                return;
                            }
                        }
                        // Actualizar el tiempo de la última interacción
                        cooldowns.put(playerUUID, currentTime);
                    }

                    Inventory inventory = player.getInventory();

                    if (itemInHand.getAmount() == 1) {
                        // Si el jugador tiene solo un cubo en la mano, convertirlo en un cubo de lava
                        itemInHand.setType(Material.LAVA_BUCKET);
                    } else {
                        // Si el jugador tiene varios cubos, disminuir la cantidad y añadir un cubo de lava al inventario
                        itemInHand.setAmount(itemInHand.getAmount() - 1);
                        ItemStack lavaBucket = new ItemStack(Material.LAVA_BUCKET);

                        // Añadir cubo de lava al inventario, o tirarlo al suelo si el inventario está lleno
                        if (inventory.addItem(lavaBucket).size() != 0) {
                            // Inventario lleno, tirar al suelo
                            player.getWorld().dropItemNaturally(player.getLocation(), lavaBucket);
                        }
                    }

                    // Cambiar el bloque de obsidiana a aire
                    block.setType(Material.AIR);
                }
            }
        }
    }
}*/
