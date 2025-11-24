package org.roland0719.AmethystAutoCraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AmethystAutoCraft extends JavaPlugin {

    private static AmethystAutoCraft instance;
    private ConfigManager configManager;
    private GuiConfigManager guiConfigManager;
    private MessageManager messageManager;
    private CraftManager craftManager;
    private GuiManager guiManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.guiConfigManager = new GuiConfigManager(this);
        this.messageManager = new MessageManager(this);
        this.craftManager = new CraftManager(this);
        this.guiManager = new GuiManager(this);

        configManager.loadConfig();
        guiConfigManager.loadConfig();
        messageManager.loadConfig();

        getCommand("autocraft").setExecutor(new AutoCraftCommand(this));
        getCommand("aautocraft").setExecutor(new AutoCraftCommand(this));

        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryUpdateListener(this), this);

        getLogger().info("AmethystAutoCraft has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AmethystAutoCraft has been disabled!");
    }

    public static AmethystAutoCraft getInstance() { return instance; }
    public ConfigManager getConfigManager() { return configManager; }
    public GuiConfigManager getGuiConfigManager() { return guiConfigManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public CraftManager getCraftManager() { return craftManager; }
    public GuiManager getGuiManager() { return guiManager; }
}
