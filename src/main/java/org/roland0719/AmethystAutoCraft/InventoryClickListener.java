package org.roland0719.AmethystAutoCraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    private final AmethystAutoCraft plugin;

    public InventoryClickListener(AmethystAutoCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() != null) return;
        if (!event.getView().getTitle().equals(plugin.getGuiConfigManager().getGuiTitle())) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null) return;

        int slot = event.getRawSlot();
        CraftManager craftManager = plugin.getCraftManager();
        CraftManager.PlayerSettings settings = craftManager.getPlayerSettings(player);

        if (slot == plugin.getGuiConfigManager().getToggleSlot()) {
            // CHECK: Permission to toggle + use permission
            if (!player.hasPermission("amethyst.autocraft.on") ||
                    !player.hasPermission("amethyst.autocraft.off") ||
                    !player.hasPermission("amethyst.autocraft.use")) {
                player.sendMessage(plugin.getMessageManager().getNoPermission());
                return;
            }

            settings.setEnabled(!settings.isEnabled());
            if (settings.isEnabled()) {
                craftManager.processAutoCraft(player);
            }
            plugin.getGuiManager().openAutoCraftGUI(player);
            player.sendMessage(settings.isEnabled() ?
                    plugin.getMessageManager().getEnabled() :
                    plugin.getMessageManager().getDisabled()
            );

        } else if (slot == plugin.getGuiConfigManager().getDirectionSlot()) {
            // CHECK: Permission to use autocraft
            if (!player.hasPermission("amethyst.autocraft.use")) {
                player.sendMessage(plugin.getMessageManager().getNoPermission());
                return;
            }

            settings.setForwardDirection(!settings.isForwardDirection());
            if (settings.isEnabled()) {
                craftManager.processAutoCraft(player);
            }
            plugin.getGuiManager().openAutoCraftGUI(player);
            player.sendMessage(settings.isForwardDirection() ?
                    plugin.getMessageManager().getDirectionForward() :
                    plugin.getMessageManager().getDirectionReverse()
            );
        }
    }
}
