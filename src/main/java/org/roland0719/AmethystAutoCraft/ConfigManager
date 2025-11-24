package org.roland0719.AmethystAutoCraft;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final AmethystAutoCraft plugin;
    private FileConfiguration config;
    private File configFile;

    private Map<Material, Material> forwardRecipes;
    private Map<Material, Material> reverseRecipes;
    private Map<Material, Integer> conversionRates;

    // Add this method to ConfigManager class
    public boolean isAutoEnableOnJoin() {
        return config.getBoolean("settings.auto_enable_on_join", false);
    }

    public ConfigManager(AmethystAutoCraft plugin) {
        this.plugin = plugin;
        this.forwardRecipes = new HashMap<>();
        this.reverseRecipes = new HashMap<>();
        this.conversionRates = new HashMap<>();
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        loadRecipes();
    }

    private void loadRecipes() {
        forwardRecipes.clear();
        reverseRecipes.clear();
        conversionRates.clear();

        if (config.contains("recipes")) {
            for (String key : config.getConfigurationSection("recipes").getKeys(false)) {
                String path = "recipes." + key;
                String fromMaterial = config.getString(path + ".from");
                String toMaterial = config.getString(path + ".to");
                int rate = config.getInt(path + ".rate", 1);

                try {
                    Material from = Material.valueOf(fromMaterial.toUpperCase());
                    Material to = Material.valueOf(toMaterial.toUpperCase());

                    forwardRecipes.put(from, to);
                    reverseRecipes.put(to, from);
                    conversionRates.put(from, rate);

                    plugin.getLogger().info("Loaded recipe: " + from + " -> " + to + " (rate: " + rate + ")");
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid material in recipe: " + key);
                }
            }
        }
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config.yml!");
        }
    }

    public void reloadConfig() {
        loadConfig();
    }

    public Map<Material, Material> getForwardRecipes() { return new HashMap<>(forwardRecipes); }
    public Map<Material, Material> getReverseRecipes() { return new HashMap<>(reverseRecipes); }
    public Map<Material, Integer> getConversionRates() { return new HashMap<>(conversionRates); }

    public Material getForwardConversion(Material from) {
        return forwardRecipes.get(from);
    }

    public Material getReverseConversion(Material from) {
        return reverseRecipes.get(from);
    }

    public int getConversionRate(Material material) {
        return conversionRates.getOrDefault(material, 1);
    }

    public void addRecipe(String key, Material from, Material to, int rate) {
        forwardRecipes.put(from, to);
        reverseRecipes.put(to, from);
        conversionRates.put(from, rate);

        config.set("recipes." + key + ".from", from.toString());
        config.set("recipes." + key + ".to", to.toString());
        config.set("recipes." + key + ".rate", rate);
        saveConfig();
    }

    public void removeRecipe(String key) {
        if (config.contains("recipes." + key)) {
            String fromMaterial = config.getString("recipes." + key + ".from");
            String toMaterial = config.getString("recipes." + key + ".to");

            try {
                Material from = Material.valueOf(fromMaterial.toUpperCase());
                Material to = Material.valueOf(toMaterial.toUpperCase());

                forwardRecipes.remove(from);
                reverseRecipes.remove(to);
                conversionRates.remove(from);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid material when removing recipe: " + key);
            }

            config.set("recipes." + key, null);
            saveConfig();
        }
    }
}
