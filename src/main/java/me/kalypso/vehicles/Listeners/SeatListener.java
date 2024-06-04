package me.kalypso.vehicles.Listeners;

import me.kalypso.vehicles.Handler.VehiclesHandler;
import me.kalypso.vehicles.Vehicles.Objects.Seat;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class SeatListener implements Listener {

    /*@EventHandler(priority = EventPriority.HIGH)
    public void onPetInteract(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (!(e.getRightClicked() instanceof Interaction i)) return;

        Seat seat = VehiclesHandler.getInstance().getSeat(i.getUniqueId());
        if (seat == null) return;

        seat.onRightClick(p);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPetDismount(EntityDismountEvent e) {

        if (!(e.getDismounted() instanceof Interaction i)) return;
        if (!(e.getEntity() instanceof Player p)) return;

        Seat seat = VehiclesHandler.getInstance().getSeat(i.getUniqueId());
        if (seat == null) return;

        if (seat.isMounted()) e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player p = e.getPlayer();

        Seat seat = VehiclesHandler.getInstance().getRiddenSeat(p.getUniqueId());
        if (seat != null) seat.dismountPassenger(p);

    }*/


}
