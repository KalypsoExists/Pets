package me.kalypso.vehicles.handler;

import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.vehicles.objects.Interactable;
import org.bukkit.Material;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InteractionHandler implements Listener {

    private static final Map<UUID, Interactable> interactables = new HashMap<>();

    public InteractionHandler() {
        Core.registerEvent(this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (!(e.getRightClicked() instanceof Interaction i)) return;
        Interactable object = interactables.get(i.getUniqueId());
        if(object == null) return;

        object.onRightClick(p);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageByEntityEvent e) {

        if(!(e.getDamager() instanceof Player p)) return;
        if(!(e.getEntity() instanceof Interaction i)) return;

        Interactable object = interactables.get(i.getUniqueId());
        if(object == null) return;

        if(p.getActiveItem().getType() == Material.AIR) object.onLeftClick(p);

    }

    public static void registerInteractable(UUID interactionUUID, Interactable i) {
        interactables.put(interactionUUID, i);
    }


}
