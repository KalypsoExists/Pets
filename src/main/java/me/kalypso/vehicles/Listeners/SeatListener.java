package me.kalypso.vehicles.Listeners;

import org.bukkit.event.Listener;

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
