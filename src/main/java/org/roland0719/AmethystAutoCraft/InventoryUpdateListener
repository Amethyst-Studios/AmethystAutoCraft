package org.roland0719.AmethystAutoCraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryUpdateListener implements Listener {

    private final AmethystAutoCraft plugin;

    public InventoryUpdateListener(AmethystAutoCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();

            // CHECK: Only process if player has autocraft enabled AND permission
            if (plugin.getCraftManager().canUseAutoCraft(player)) {
                plugin.getCraftManager().processAutoCraft(player);
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();

            // CHECK: Only process if player has autocraft enabled AND permission
            if (plugin.getCraftManager().canUseAutoCraft(player)) {
                plugin.getCraftManager().processAutoCraft(player);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Auto-enable if player has permission (optional feature)
        if (player.hasPermission("amethyst.autocraft.use") && plugin.getConfigManager().isAutoEnableOnJoin()) {
            plugin.getCraftManager().getPlayerSettings(player).setEnabled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Future: Save player settings to file
    }
}
