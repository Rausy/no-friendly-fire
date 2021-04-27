

package com.raus.noFriendlyFire;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Objects;

public class FriendlyFireListener implements Listener {
    noFriendlyFire plugin;
    public FriendlyFireListener(noFriendlyFire plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onHit(EntityDamageByEntityEvent event) {
        Entity ent = event.getEntity();
        Entity dmg = event.getDamager();
        if (dmg instanceof Projectile) {
            dmg = (Entity)((Projectile)dmg).getShooter();
        }

        if (ent instanceof Tameable && dmg instanceof AnimalTamer) {
            Tameable pet = (Tameable)ent;
            AnimalTamer damager = (AnimalTamer)dmg;
            if (noFriendlyFire.isNoFF(damager.getUniqueId()) && Objects.equals(pet.getOwner(), damager)) {
                event.setDamage(0.0D);
                event.setCancelled(true);
            }
        }

    }
}
