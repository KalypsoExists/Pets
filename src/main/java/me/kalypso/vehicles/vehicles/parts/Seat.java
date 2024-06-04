package me.kalypso.vehicles.vehicles.parts;

import lombok.Getter;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.handler.InteractionHandler;
import me.kalypso.vehicles.vehicles.objects.Interactable;
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

    private final Core core;
    private final boolean driverSeat;

    public Seat(@NotNull Core core, @NotNull String name, @NotNull Frame frame) {
        super(core, name, frame);

        this.core = core;
        driverSeat = false;

        //i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());

        core.registerEvent(this);
        InteractionHandler.registerInteractable(getInteraction().getUniqueId(), this);
    }

    public Seat(@NotNull Core core, @NotNull String name, @NotNull Frame frame, boolean driverSeat) {
        super(core, name, frame);

        this.core = core;
        this.driverSeat = driverSeat;

        //i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());

        core.registerEvent(this);
        InteractionHandler.registerInteractable(getInteraction().getUniqueId(), this);
    }

    // PASSENGER HANDLING

    @Getter
    private boolean mounted = false;

    @Override
    public void onRightClick(Player player) {
        mountPassenger(player);
    }

    @Override
    public void onLeftClick(Player player) {}

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
