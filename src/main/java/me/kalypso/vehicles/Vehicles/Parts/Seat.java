package me.kalypso.vehicles.Vehicles.Parts;

import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Handler.InteractionHandler;
import me.kalypso.vehicles.Vehicles.Objects.Interactable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class Seat extends Frame implements Interactable, Listener {

    private final boolean driverSeat;

    public Seat(@NotNull String name, @NotNull Frame frame) {
        super(name, frame);

        driverSeat = false;

        //i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());

        Core.registerEvent(this);
        InteractionHandler.registerInteractable(getInteraction().getUniqueId(), this);
    }

    public Seat(@NotNull String name, @NotNull Frame frame, boolean driverSeat) {
        super(name, frame);

        this.driverSeat = driverSeat;

        //i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());

        Core.registerEvent(this);
        InteractionHandler.registerInteractable(getInteraction().getUniqueId(), this);
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

    public void mountPassenger(Player passenger) {

        mounted = true;
        getInteraction().addPassenger(passenger);

        if(driverSeat) getVehicle().addDriver(passenger.getUniqueId());

    }

    public void dismountPassenger(OfflinePlayer player) {

        Entity entity = null;

        for (Entity e : getInteraction().getPassengers())
            if(e instanceof Player p)
                if(p.getUniqueId().equals(player.getUniqueId()))  {
                    entity = e;
                }

        if(entity != null) {
            if(getInteraction().getPassengers().isEmpty()) mounted = false;
            getInteraction().removePassenger(entity);
            if(driverSeat) getVehicle().removeDriver(player.getUniqueId());
        }

    }

    public void emptyPassengers() {

        mounted = false;

        for (Entity e : getInteraction().getPassengers()) {
            getInteraction().removePassenger(e);
            if(driverSeat) getVehicle().removeDriver(e.getUniqueId());
        }

    }

    public Entity getMountedPassenger() {

        for (Entity e : getInteraction().getPassengers()) {
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
        if(!i.getUniqueId().equals(getInteraction().getUniqueId())) return;

        if (isMounted()) e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player p = e.getPlayer();
        if(!getMountedPassenger().getUniqueId().equals(p.getUniqueId())) return;
        dismountPassenger(p);

    }


}
