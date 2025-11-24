package org.roland0719.AmethystAutoCraft;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MessageManager {

    private final AmethystAutoCraft plugin;
    private FileConfiguration messageConfig;
    private File messageConfigFile;

    public MessageManager(AmethystAutoCraft plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        messageConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messageConfigFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messageConfig = YamlConfiguration.loadConfiguration(messageConfigFile);
    }

    public void saveConfig() {
        try {
            messageConfig.save(messageConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save messages.yml!");
        }
    }

    public void reloadConfig() {
        loadConfig();
    }

    private String getMessage(String path, String defaultValue) {
        String message = messageConfig.getString(path, defaultValue);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private List<String> getMessages(String path, List<String> defaultValue) {
        List<String> messages = messageConfig.getStringList(path);
        if (messages.isEmpty()) return defaultValue;
        return messages.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
    }

    // AutoCraft Messages
    public String getEnabled() { return getMessage("autocraft.enabled", "&aAutoCraft has been enabled!"); }
    public String getDisabled() { return getMessage("autocraft.disabled", "&cAutoCraft has been disabled!"); }
    public String getDirectionForward() { return getMessage("autocraft.direction_forward", "&6Crafting direction set to: &bForward"); }
    public String getDirectionReverse() { return getMessage("autocraft.direction_reverse", "&6Crafting direction set to: &eReverse"); }
    public String getStatusMessage() { return getMessage("autocraft.status", "&6AutoCraft Status:\n&7Enabled: {enabled}\n&7Permission: {permission}\n&7Direction: {direction}\n&7Status: {active}"); }

    // Error Messages
    public String getNoPermission() { return getMessage("errors.no_permission", "&cYou don't have permission for this!"); }
    public String getPlayerOnly() { return getMessage("errors.player_only", "&cOnly players can use this command!"); }
    public String getInvalidMaterial() { return getMessage("errors.invalid_material", "&cInvalid material specified!"); }
    public String getNoAutoCraftPermission() { return getMessage("errors.no_autocraft_permission", "&cYou don't have permission to use AutoCraft!"); }

    // Command Messages
    public String getHelp() {
        return getMessage("commands.help",
                "&6AutoCraft Commands:\n" +
                        "&f/autocraft on &7- Enable AutoCraft\n" +
                        "&f/autocraft off &7- Disable AutoCraft\n" +
                        "&f/autocraft gui &7- Open GUI\n" +
                        "&f/autocraft status &7- Check status\n" +
                        "&f/autocraft reload &7- Reload configuration");
    }

    public String getReloaded() { return getMessage("commands.reloaded", "&aConfiguration reloaded!"); }

    // Custom message setter
    public void setMessage(String path, String message) {
        messageConfig.set(path, message);
        saveConfig();
    }

    // Get raw message (without color codes)
    public String getRawMessage(String path, String defaultValue) {
        return messageConfig.getString(path, defaultValue);
    }
}
