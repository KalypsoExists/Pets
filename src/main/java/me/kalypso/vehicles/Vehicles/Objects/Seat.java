package me.kalypso.vehicles.Vehicles.Objects;

import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Handler.InteractionHandler;
import me.kalypso.vehicles.Handler.VehiclesHandler;
import me.kalypso.vehicles.Vehicles.Vehicle;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class Seat extends VehiclePart implements Interactable, Listener {

    private final Frame frame;
    private final boolean driverSeat;

    public Seat(@NotNull Vehicle vehicle, @NotNull Frame frame, boolean driverSeat) {
        super(vehicle);

        this.frame = frame;
        this.driverSeat = driverSeat;

        setupInteraction();
    }

    public Seat(@NotNull Vehicle vehicle, @NotNull Frame frame) {
        super(vehicle);

        this.frame = frame;
        driverSeat = false;

        setupInteraction();
    }

    private void setupInteraction() {
        Interaction i = frame.getInteraction();
        i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());

        Core.registerEvent(this);
        InteractionHandler.registerInteractable(i.getUniqueId(), this);
    }

    public Frame getFrame() {
        return frame;
    }

    // PASSENGER HANDLING

    private boolean mounted = false;

    @Override
    public void onRightClick(Player player) {
        mountPassenger(player);
    }

    @Override
    public void onLeftClick(Player player) {}

    public boolean isMounted() {
        return mounted;
    }

    public boolean isDriverSeat() {
        return driverSeat;
    }

    public void mountPassenger(Player passenger) {

        mounted = true;
        frame.getInteraction().addPassenger(passenger);

    }

    public void dismountPassenger(OfflinePlayer player) {

        Entity entity = null;

        for (Entity e : frame.getInteraction().getPassengers())
            if(e instanceof Player p)
                if(p.getUniqueId().equals(player.getUniqueId()))  {
                    entity = e;
                }

        if(entity != null) {
            if(frame.getInteraction().getPassengers().isEmpty()) mounted = false;
            frame.getInteraction().removePassenger(entity);
        }

    }

    public void emptyPassengers() {

        mounted = false;

        for (Entity e : frame.getInteraction().getPassengers())
            frame.getInteraction().removePassenger(e);

    }

    public Entity getMountedPassenger() {

        for (Entity e : frame.getInteraction().getPassengers()) {
            if (e instanceof Player) {
                return (Player) e;
            }
        }

        return null;

    }

    // LISTENERS

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDismount(EntityDismountEvent e) {

        if (!(e.getDismounted() instanceof Interaction i)) return;
        if (!(e.getEntity() instanceof Player p)) return;
        if(!i.getUniqueId().equals(getFrame().getInteraction().getUniqueId())) return;

        if (isMounted()) e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player p = e.getPlayer();
        if(!getMountedPassenger().getUniqueId().equals(p.getUniqueId())) return;
        dismountPassenger(p);

    }


}
