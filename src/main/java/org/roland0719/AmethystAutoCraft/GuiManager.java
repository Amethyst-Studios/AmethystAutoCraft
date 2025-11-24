package org.roland0719.AmethystAutoCraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GuiManager {

    private final AmethystAutoCraft plugin;

    public GuiManager(AmethystAutoCraft plugin) {
        this.plugin = plugin;
    }

    public void openAutoCraftGUI(Player player) {
        String title = plugin.getGuiConfigManager().getGuiTitle();
        int size = plugin.getGuiConfigManager().getGuiSize();

        Inventory gui = Bukkit.createInventory(null, size, title);

        CraftManager.PlayerSettings settings = plugin.getCraftManager().getPlayerSettings(player);

        ItemStack toggleItem = createToggleItem(settings.isEnabled());
        gui.setItem(plugin.getGuiConfigManager().getToggleSlot(), toggleItem);

        ItemStack directionItem = createDirectionItem(settings.isForwardDirection());
        gui.setItem(plugin.getGuiConfigManager().getDirectionSlot(), directionItem);

        ItemStack infoItem = createInfoItem();
        gui.setItem(plugin.getGuiConfigManager().getInfoSlot(), infoItem);

        player.openInventory(gui);
    }

    private ItemStack createToggleItem(boolean enabled) {
        Material material = enabled ?
                plugin.getGuiConfigManager().getToggleOnMaterial() :
                plugin.getGuiConfigManager().getToggleOffMaterial();

        String name = enabled ?
                plugin.getGuiConfigManager().getToggleOnName() :
                plugin.getGuiConfigManager().getToggleOffName();

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(colorize(name));

        List<String> lore = Arrays.asList(
                colorize("&7Click to toggle"),
                "",
                enabled ? colorize("&a✔ Active") : colorize("&c✖ Inactive")
        );
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createDirectionItem(boolean forwardDirection) {
        ItemStack item = new ItemStack(plugin.getGuiConfigManager().getDirectionMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(colorize(plugin.getGuiConfigManager().getDirectionName()));

        List<String> lore = Arrays.asList(
                colorize("&7Current: " + (forwardDirection ? "&bForward" : "&eReverse")),
                "",
                colorize("&7Forward: &fMaterial → Product"),
                colorize("&7Reverse: &fProduct → Material"),
                "",
                colorize("&7Click to toggle")
        );
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createInfoItem() {
        ItemStack item = new ItemStack(plugin.getGuiConfigManager().getInfoMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(colorize(plugin.getGuiConfigManager().getInfoName()));

        List<String> lore = Arrays.asList(
                colorize("&7AutoCraft Plugin"),
                colorize("&7Automatically converts materials"),
                colorize("&7based on your settings."),
                "",
                colorize("&6Permissions:"),
                colorize("&f- amethyst.autocraft.on"),
                colorize("&f- amethyst.autocraft.off"),
                colorize("&f- amethyst.autocraft.gui")
        );
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    private String colorize(String text) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', text);
    }

    private List<String> colorize(List<String> texts) {
        return texts.stream()
                .map(this::colorize)
                .collect(Collectors.toList());
    }
}
