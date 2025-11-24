package org.roland0719.AmethystAutoCraft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CraftManager {

    private final AmethystAutoCraft plugin;
    private Map<UUID, PlayerSettings> playerSettings;

    public CraftManager(AmethystAutoCraft plugin) {
        this.plugin = plugin;
        this.playerSettings = new HashMap<>();
    }

    public class PlayerSettings {
        private boolean enabled = false;
        private boolean forwardDirection = true;

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }

        public boolean isForwardDirection() { return forwardDirection; }
        public void setForwardDirection(boolean forwardDirection) { this.forwardDirection = forwardDirection; }
    }

    public PlayerSettings getPlayerSettings(Player player) {
        return playerSettings.computeIfAbsent(player.getUniqueId(), k -> new PlayerSettings());
    }

    public void processAutoCraft(Player player) {
        PlayerSettings settings = getPlayerSettings(player);

        // CHECK: Only process if enabled AND player has permission
        if (!settings.isEnabled() || !player.hasPermission("amethyst.autocraft.use")) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerInventory inventory = player.getInventory();
                boolean changed = false;

                for (int i = 0; i < inventory.getSize(); i++) {
                    ItemStack item = inventory.getItem(i);
                    if (item == null || item.getType() == Material.AIR) continue;

                    Material result = null;
                    int rate = 1;

                    if (settings.isForwardDirection()) {
                        result = plugin.getConfigManager().getForwardConversion(item.getType());
                    } else {
                        result = plugin.getConfigManager().getReverseConversion(item.getType());
                    }

                    if (result != null) {
                        rate = plugin.getConfigManager().getConversionRate(item.getType());

                        if (item.getAmount() >= rate) {
                            int conversions = item.getAmount() / rate;
                            int remaining = item.getAmount() % rate;

                            ItemStack newItem = new ItemStack(result, conversions);
                            inventory.setItem(i, newItem);

                            if (remaining > 0) {
                                ItemStack remainingItem = new ItemStack(item.getType(), remaining);
                                HashMap<Integer, ItemStack> leftover = inventory.addItem(remainingItem);
                                if (!leftover.isEmpty()) {
                                    player.getWorld().dropItem(player.getLocation(), leftover.get(0));
                                }
                            }

                            changed = true;
                        }
                    }
                }

                if (changed) {
                    player.updateInventory();
                }
            }
        }.runTask(plugin);
    }

    public void toggleAutoCraft(Player player, boolean enable) {
        // CHECK: Permission required to toggle
        if (!player.hasPermission("amethyst.autocraft.use")) {
            player.sendMessage(plugin.getMessageManager().getNoPermission());
            return;
        }

        PlayerSettings settings = getPlayerSettings(player);
        settings.setEnabled(enable);

        if (enable) {
            processAutoCraft(player);
        }
    }

    public void toggleDirection(Player player, boolean forward) {
        // CHECK: Permission required to change direction
        if (!player.hasPermission("amethyst.autocraft.use")) {
            player.sendMessage(plugin.getMessageManager().getNoPermission());
            return;
        }

        PlayerSettings settings = getPlayerSettings(player);
        settings.setForwardDirection(forward);

        if (settings.isEnabled()) {
            processAutoCraft(player);
        }
    }

    // Check if player can use autocraft (enabled + permission)
    public boolean canUseAutoCraft(Player player) {
        PlayerSettings settings = getPlayerSettings(player);
        return settings.isEnabled() && player.hasPermission("amethyst.autocraft.use");
    }

    // Get all player settings (for admin purposes)
    public Map<UUID, PlayerSettings> getAllPlayerSettings() {
        return new HashMap<>(playerSettings);
    }

    // Clear player settings (for reloads)
    public void clearPlayerSettings() {
        playerSettings.clear();
    }
}
