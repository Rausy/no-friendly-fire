package com.raus.noFriendlyFire;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ToggleCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 0)
		{
			return false;
		}
		else if (args[0].equals("info"))
		{
			// Send message
			sender.sendMessage(ChatColor.GREEN + "[NoFF]" + ChatColor.GRAY + " Version " + Main.getInstance().getDescription().getVersion());
			return true;
		}
		else if (args[0].equals("toggle"))
		{
			if (!sender.hasPermission("nofriendlyfire.toggle"))
			{
				// Send message
				sender.sendMessage(ChatColor.GREEN + "[NoFF]" + ChatColor.RED + " You do not have permission to run this command.");
				return true;
			}
			else if (!(sender instanceof Player))
			{
				// Send message
				sender.sendMessage(ChatColor.GREEN + "[NoFF]" + ChatColor.RED + " This command cannot be run from the console.");
				return true;
			}
			
			Player ply = (Player) sender;
			boolean toggle = Main.friendlyFire.containsKey(ply.getUniqueId()) ?  Main.friendlyFire.get(ply.getUniqueId()) : true;
			
			// Do stuff
			if (toggle)
			{
				sender.sendMessage(ChatColor.GREEN + "[NoFF]" + ChatColor.GRAY + " You turned on friendly fire.");
				Main.friendlyFire.put(ply.getUniqueId(), false);
			}
			else
			{
				sender.sendMessage(ChatColor.GREEN + "[NoFF]" + ChatColor.GRAY + " You turned off friendly fire.");
				Main.friendlyFire.put(ply.getUniqueId(), true);
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
}