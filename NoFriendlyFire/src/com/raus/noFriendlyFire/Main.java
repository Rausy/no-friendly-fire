package com.raus.noFriendlyFire;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	private static Main main;
	public static HashMap<UUID, Boolean> friendlyFire = new HashMap<UUID, Boolean>();
	
	public Main()
	{
		main = this;
	}
	
	@Override
	public void onEnable()
	{
		// Register command
		this.getCommand("noff").setExecutor(new ToggleCommand());
		
		// Listeners
		getServer().getPluginManager().registerEvents(new FriendlyFireListener(), this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public static Main getInstance()
	{
		return main;
	}
}