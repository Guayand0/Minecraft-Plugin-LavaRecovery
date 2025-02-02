package com.Guayand0.events;

import com.Guayand0.LavaRecovery;
import com.Guayand0.managers.LanguageManager;
import com.Guayand0.managers.PlayerManager;
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
    private final PlayerManager playerManager;
    private final HashMap<UUID, Long> cooldowns;

    private final MessageUtils MU = new MessageUtils();

    public PlayerInteract(LavaRecovery plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
        this.playerManager = plugin.getPlayerManager();
        this.cooldowns = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        boolean lavaRecovery = plugin.getConfig().getBoolean("config.lava-recovery", true);
        List<String> allowedWorlds = plugin.getConfig().getStringList("config.allowed-worlds");
        int cooldownTime = plugin.getConfig().getInt("config.lava-recovery-cooldown", 60);
        int maxRecoveryTimesPlayer = plugin.getConfig().getInt("config.max-recovery-times-player", -1);

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        String worldName = player.getWorld().getName();

        String playerPath = "player." + player.getUniqueId() + "." + player.getName();
        int recoveryTimes = playerManager.getPlayer().getInt(playerPath + ".allowed-worlds-name." + worldName + ".recovery-times");
        long currentTime = System.currentTimeMillis();
        long lastUseTime = cooldowns.getOrDefault(playerUUID, 0L);
        long timeRemaining = (cooldownTime * 1000) - (currentTime - lastUseTime);

        if (!lavaRecovery) {
            // Si obsidian-to-lava es false, no hacer nada
            return;
        }

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

            // Si el jugador es OP o tiene permisos de admin no tiene limite de usos ni de espera
            if (player.isOp() || player.hasPermission("lavarecovery.admin")) {
                replaceObsidianLava(player, itemInHand, block);
                return;
            }

            // Verifica si recoveryTimes es igual a maxRecoveryTimesPlayer y envia el mensaje de maximo alcanzado
            if (recoveryTimes == maxRecoveryTimesPlayer) {
                String message = languageManager.getMessage("config.max-lava-recovery-reached");
                if (message != null) {
                    player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
                }
                return;
            }

            // Si el tiempo de cooldown no ha terminado
            if (timeRemaining > 0) {
                String message = languageManager.getMessage("config.cooldown-message");
                if (message != null) {
                    plugin.replacePluginPlaceholders.put("%timereaming%", String.valueOf(timeRemaining / 1000));
                    player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
                }
                return;
            }

            // Si el cooldown está activo y el jugador no tiene permiso para evitarlo
            if (cooldownTime > 0 && !player.hasPermission("lavarecovery.cooldown.exempt")) {
                cooldowns.put(playerUUID, currentTime);
            }

            // Si el límite de recuperación está desactivado o el jugador aún tiene intentos disponibles
            if (maxRecoveryTimesPlayer < 0 || recoveryTimes < maxRecoveryTimesPlayer) {
                replaceObsidianLava(player, itemInHand, block);

                // Si el límite de recuperación está definido, aumenta los intentos
                if (maxRecoveryTimesPlayer >= 0) {
                    playerManager.getPlayer().set(playerPath + ".allowed-worlds-name." + worldName + ".recovery-times", recoveryTimes + 1);
                    playerManager.savePlayer();

                    String message = languageManager.getMessage("config.lava-recovery-times");
                    if (message != null) {
                        plugin.replacePluginPlaceholders.put("%lavatimes%", String.valueOf(recoveryTimes + 1));
                        plugin.replacePluginPlaceholders.put("%maxlavatimes%", String.valueOf(maxRecoveryTimesPlayer));
                        player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
                    }
                }
            } else {
                // Si se alcanzó el máximo de intentos, muestra el mensaje correspondiente
                String message = languageManager.getMessage("config.max-lava-recovery-reached");
                if (message != null) {
                    player.sendMessage(MU.getCheckAllPlaceholdersText(plugin.getPlaceholderAPI(), player, message, plugin.replacePluginPlaceholders));
                }
            }
        }
    }

    public void replaceObsidianLava(Player player, ItemStack itemInHand, Block block) {
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
