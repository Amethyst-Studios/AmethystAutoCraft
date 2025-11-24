package org.roland0719.AmethystAutoCraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AutoCraftCommand implements CommandExecutor, TabCompleter {

    private final AmethystAutoCraft plugin;

    public AutoCraftCommand(AmethystAutoCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessageManager().getPlayerOnly());
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            // CHECK: Permission for GUI
            if (!player.hasPermission("amethyst.autocraft.gui")) {
                player.sendMessage(plugin.getMessageManager().getNoPermission());
                return true;
            }
            plugin.getGuiManager().openAutoCraftGUI(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "on":
                // CHECK: Permission to enable + use permission
                if (!player.hasPermission("amethyst.autocraft.on") || !player.hasPermission("amethyst.autocraft.use")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                plugin.getCraftManager().toggleAutoCraft(player, true);
                player.sendMessage(plugin.getMessageManager().getEnabled());
                break;

            case "off":
                // CHECK: Permission to disable
                if (!player.hasPermission("amethyst.autocraft.off")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                plugin.getCraftManager().toggleAutoCraft(player, false);
                player.sendMessage(plugin.getMessageManager().getDisabled());
                break;

            case "gui":
                // CHECK: Permission for GUI
                if (!player.hasPermission("amethyst.autocraft.gui")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                plugin.getGuiManager().openAutoCraftGUI(player);
                break;

            case "reload":
                // CHECK: Permission for reload
                if (!player.hasPermission("amethyst.autocraft.reload")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                plugin.getConfigManager().reloadConfig();
                plugin.getGuiConfigManager().reloadConfig();
                plugin.getMessageManager().reloadConfig();
                player.sendMessage(plugin.getMessageManager().getReloaded());
                break;

            case "status":
                sendStatusInfo(player);
                break;

            default:
                player.sendMessage(plugin.getMessageManager().getHelp());
                break;
        }

        return true;
    }

    private void sendStatusInfo(Player player) {
        CraftManager.PlayerSettings settings = plugin.getCraftManager().getPlayerSettings(player);
        boolean hasUsePermission = player.hasPermission("amethyst.autocraft.use");
        boolean hasOnPermission = player.hasPermission("amethyst.autocraft.on");
        boolean hasOffPermission = player.hasPermission("amethyst.autocraft.off");
        boolean hasGuiPermission = player.hasPermission("amethyst.autocraft.gui");

        boolean isActive = settings.isEnabled() && hasUsePermission;

        String statusMessage = plugin.getMessageManager().getStatusMessage()
                .replace("{enabled}", settings.isEnabled() ? "&aENABLED" : "&cDISABLED")
                .replace("{permission}", hasUsePermission ? "&aYES" : "&cNO")
                .replace("{direction}", settings.isForwardDirection() ? "&bFORWARD" : "&eREVERSE")
                .replace("{active}", isActive ? "&aACTIVE" : "&cINACTIVE");

        player.sendMessage(statusMessage);

        // Additional permission info
        if (!hasUsePermission) {
            player.sendMessage(plugin.getMessageManager().getNoAutoCraftPermission());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("on");
            completions.add("off");
            completions.add("gui");
            completions.add("status");
            if (sender.hasPermission("amethyst.autocraft.reload")) {
                completions.add("reload");
            }
        }

        return completions;
    }
}
