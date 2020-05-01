package com.raus.noFriendlyFire;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FriendlyFireListener implements Listener
{
	@EventHandler(priority = EventPriority.LOW)
	public void onHit(EntityDamageByEntityEvent event)
	{
		// Check if it is a tameable and an animal tamer
		Entity ent = event.getEntity();
		Entity dmg = event.getDamager();
		
		// We want the shooter not an arrow
		if (dmg instanceof Arrow)
		{
			dmg = (Entity) ((Arrow) dmg).getShooter();
		}
		
		if (ent instanceof Tameable && dmg instanceof AnimalTamer)
		{
			// Check if pet is tamed and hit by owner
			Tameable pet = (Tameable) ent;
			AnimalTamer owner = (AnimalTamer) dmg;
			
			// Check toggle
			boolean toggle = Main.friendlyFire.containsKey(owner.getUniqueId()) ?  Main.friendlyFire.get(owner.getUniqueId()) : true;
			
			if (toggle && pet.isTamed() && pet.getOwner().equals(owner))
			{
				event.setDamage(0);
				event.setCancelled(true);
			}
		}
	}
}