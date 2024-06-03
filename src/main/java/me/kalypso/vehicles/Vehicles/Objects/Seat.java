package me.kalypso.vehicles.Vehicles.Objects;

import me.kalypso.vehicles.Handler.VehiclesHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Seat implements Interactable {

    private final Frame frame;
    private final boolean driverSeat;

    public Seat(@NotNull Frame frame, boolean driverSeat) {
        this.frame = frame;
        this.driverSeat = driverSeat;
    }

    public Seat(@NotNull Frame frame) {
        this.frame = frame;
        driverSeat = false;
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
        VehiclesHandler.getInstance().addRiddenSeat(passenger.getUniqueId(), this);

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

}
