//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.raus.noFriendlyFire;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements CommandExecutor {
    noFriendlyFire plugin;
    public ToggleCommand(noFriendlyFire plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        } else if (args[0].equals("info")) {
            sender.sendMessage(plugin.getMessage(Messages.VERSION_INFO, plugin.getDescription().getVersion()));
            return true;
        } else if (args[0].equals("toggle")) {
            if (!sender.hasPermission("nofriendlyfire.toggle")) {
                sender.sendMessage(plugin.getMessage(Messages.NO_PERMS));
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.getMessage(Messages.PLAYER_ONLY_COMMAND));
                return true;
            }

            Player ply = (Player)sender;
            boolean state = noFriendlyFire.isNoFF(ply.getUniqueId());
            noFriendlyFire.setNoFF(ply.getUniqueId(), !state);
            if (!state) {
                sender.sendMessage(plugin.getMessage(Messages.PROTECTION_ENABLED));
            } else {
                sender.sendMessage(plugin.getMessage(Messages.PROTECTION_DISABLED));
            }

            return true;
        }else {
            return false;
        }
    }
}
