package org.roland0719.AmethystAutoCraft;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GuiConfigManager {

    private final AmethystAutoCraft plugin;
    private FileConfiguration guiConfig;
    private File guiConfigFile;

    public GuiConfigManager(AmethystAutoCraft plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        guiConfigFile = new File(plugin.getDataFolder(), "gui.yml");
        if (!guiConfigFile.exists()) {
            plugin.saveResource("gui.yml", false);
        }

        guiConfig = YamlConfiguration.loadConfiguration(guiConfigFile);
    }

    public void saveConfig() {
        try {
            guiConfig.save(guiConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save gui.yml!");
        }
    }

    public void reloadConfig() {
        loadConfig();
    }

    public String getGuiTitle() {
        return guiConfig.getString("gui.title", "AutoCraft Settings");
    }

    public void setGuiTitle(String title) {
        guiConfig.set("gui.title", title);
        saveConfig();
    }

    public int getGuiSize() {
        return guiConfig.getInt("gui.size", 27);
    }

    public void setGuiSize(int size) {
        guiConfig.set("gui.size", size);
        saveConfig();
    }

    public Material getToggleOnMaterial() {
        return Material.valueOf(guiConfig.getString("gui.items.toggle_on.material", "LIME_DYE"));
    }

    public void setToggleOnMaterial(Material material) {
        guiConfig.set("gui.items.toggle_on.material", material.toString());
        saveConfig();
    }

    public Material getToggleOffMaterial() {
        return Material.valueOf(guiConfig.getString("gui.items.toggle_off.material", "GRAY_DYE"));
    }

    public void setToggleOffMaterial(Material material) {
        guiConfig.set("gui.items.toggle_off.material", material.toString());
        saveConfig();
    }

    public String getToggleOnName() {
        return guiConfig.getString("gui.items.toggle_on.name", "&aAutoCraft Enabled");
    }

    public void setToggleOnName(String name) {
        guiConfig.set("gui.items.toggle_on.name", name);
        saveConfig();
    }

    public String getToggleOffName() {
        return guiConfig.getString("gui.items.toggle_off.name", "&cAutoCraft Disabled");
    }

    public void setToggleOffName(String name) {
        guiConfig.set("gui.items.toggle_off.name", name);
        saveConfig();
    }

    public List<String> getToggleLore() {
        return guiConfig.getStringList("gui.items.toggle_on.lore");
    }

    public Material getDirectionMaterial() {
        return Material.valueOf(guiConfig.getString("gui.items.direction.material", "IRON_INGOT"));
    }

    public void setDirectionMaterial(Material material) {
        guiConfig.set("gui.items.direction.material", material.toString());
        saveConfig();
    }

    public String getDirectionName() {
        return guiConfig.getString("gui.items.direction.name", "&6Crafting Direction");
    }

    public void setDirectionName(String name) {
        guiConfig.set("gui.items.direction.name", name);
        saveConfig();
    }

    public List<String> getDirectionLore() {
        return guiConfig.getStringList("gui.items.direction.lore");
    }

    public Material getInfoMaterial() {
        return Material.valueOf(guiConfig.getString("gui.items.info.material", "BOOK"));
    }

    public void setInfoMaterial(Material material) {
        guiConfig.set("gui.items.info.material", material.toString());
        saveConfig();
    }

    public String getInfoName() {
        return guiConfig.getString("gui.items.info.name", "&eInformation");
    }

    public void setInfoName(String name) {
        guiConfig.set("gui.items.info.name", name);
        saveConfig();
    }

    public List<String> getInfoLore() {
        return guiConfig.getStringList("gui.items.info.lore");
    }

    public int getToggleSlot() { return guiConfig.getInt("gui.slots.toggle", 11); }
    public int getDirectionSlot() { return guiConfig.getInt("gui.slots.direction", 13); }
    public int getInfoSlot() { return guiConfig.getInt("gui.slots.info", 15); }

    public void setToggleSlot(int slot) { guiConfig.set("gui.slots.toggle", slot); saveConfig(); }
    public void setDirectionSlot(int slot) { guiConfig.set("gui.slots.direction", slot); saveConfig(); }
    public void setInfoSlot(int slot) { guiConfig.set("gui.slots.info", slot); saveConfig(); }
}
