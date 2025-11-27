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
            if (!player.hasPermission("amethyst.autocraft.gui")) {
                player.sendMessage(plugin.getMessageManager().getNoPermission());
                return true;
            }
            plugin.getGuiManager().openAutoCraftGUI(player);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "on":
                if (!player.hasPermission("amethyst.autocraft.on") || !player.hasPermission("amethyst.autocraft.use")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                
                // Check if direction is specified
                if (args.length >= 2) {
                    String direction = args[1].toLowerCase();
                    if (direction.equals("forward") || direction.equals("f")) {
                        plugin.getCraftManager().getPlayerSettings(player).setForwardDirection(true);
                        plugin.getCraftManager().toggleAutoCraft(player, true);
                        player.sendMessage(plugin.getMessageManager().getEnabled() + " " + plugin.getMessageManager().getDirectionForward());
                    } else if (direction.equals("reverse") || direction.equals("r") || direction.equals("backward")) {
                        plugin.getCraftManager().getPlayerSettings(player).setForwardDirection(false);
                        plugin.getCraftManager().toggleAutoCraft(player, true);
                        player.sendMessage(plugin.getMessageManager().getEnabled() + " " + plugin.getMessageManager().getDirectionReverse());
                    } else {
                        player.sendMessage(plugin.getMessageManager().getInvalidDirection());
                    }
                } else {
                    // No direction specified, just enable
                    plugin.getCraftManager().toggleAutoCraft(player, true);
                    player.sendMessage(plugin.getMessageManager().getEnabled());
                }
                break;
                
            case "off":
                if (!player.hasPermission("amethyst.autocraft.off")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                plugin.getCraftManager().toggleAutoCraft(player, false);
                player.sendMessage(plugin.getMessageManager().getDisabled());
                break;
                
            case "gui":
                if (!player.hasPermission("amethyst.autocraft.gui")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                plugin.getGuiManager().openAutoCraftGUI(player);
                break;
                
            case "reload":
                if (!player.hasPermission("amethyst.autocraft.reload")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                plugin.getConfigManager().reloadConfig();
                plugin.getGuiConfigManager().reloadConfig();
                plugin.getMessageManager().reloadConfig();
                player.sendMessage(plugin.getMessageManager().getReloaded());
                break;
                
            case "direction":
            case "dir":
                if (!player.hasPermission("amethyst.autocraft.use")) {
                    player.sendMessage(plugin.getMessageManager().getNoPermission());
                    return true;
                }
                
                if (args.length >= 2) {
                    String direction = args[1].toLowerCase();
                    if (direction.equals("forward") || direction.equals("f")) {
                        plugin.getCraftManager().toggleDirection(player, true);
                        player.sendMessage(plugin.getMessageManager().getDirectionForward());
                    } else if (direction.equals("reverse") || direction.equals("r") || direction.equals("backward")) {
                        plugin.getCraftManager().toggleDirection(player, false);
                        player.sendMessage(plugin.getMessageManager().getDirectionReverse());
                    } else {
                        player.sendMessage(plugin.getMessageManager().getInvalidDirection());
                    }
                } else {
                    // Toggle direction if no argument
                    boolean current = plugin.getCraftManager().getPlayerSettings(player).isForwardDirection();
                    plugin.getCraftManager().toggleDirection(player, !current);
                    player.sendMessage(!current ? 
                        plugin.getMessageManager().getDirectionForward() : 
                        plugin.getMessageManager().getDirectionReverse()
                    );
                }
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
            completions.add("direction");
            if (sender.hasPermission("amethyst.autocraft.reload")) {
                completions.add("reload");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("direction") || args[0].equalsIgnoreCase("dir")) {
                completions.add("forward");
                completions.add("reverse");
                completions.add("f");
                completions.add("r");
            }
        }
        
        return completions;
    }
}
