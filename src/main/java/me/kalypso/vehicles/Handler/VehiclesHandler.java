package me.kalypso.vehicles.Handler;

import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Vehicles.Vehicle;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VehiclesHandler {

    public static final NamespacedKey key = new NamespacedKey(Core.getInstance(), "vehicle");

    private static final Map<UUID, Vehicle> vehicles = new HashMap<>();

    public static void registerVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
    }

    public static void unregisterVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle.getId());
    }

    public static void unregisterVehicle(UUID vehicleUUID) {
        vehicles.remove(vehicleUUID);
    }

    // MAP HANDLING

    /*public void addAliveVehicle(Vehicle vehicle) {
        aliveVehicles.put(vehicle.getId(), vehicle);
    }

    public void addAliveFrame(Frame frame) {
        aliveFrames.put(frame.getInteraction().getUniqueId(), frame);
    }

    public void addAliveSeat(Seat seat, Vehicle vehicle) {
        aliveSeatsVehicle.put(seat, vehicle);
        aliveSeatsInteraction.put(seat.getFrame().getInteraction().getUniqueId(), seat);
    }

    public void addRiddenSeat(UUID player, Seat seat) {
        riddenSeats.put(player, seat);
    }

    public Seat getRiddenSeat(UUID player) {
        return riddenSeats.get(player);
    }

    public boolean isRidingSeat(UUID player) {
        return getRiddenSeat(player) != null;
    }

    public Vehicle getVehicle(Seat seat) {
        return aliveSeatsVehicle.get(seat);
    }

    public Vehicle getVehicleIfDriver(UUID player) {
        Seat seat = getRiddenSeat(player);
        if(seat.isDriverSeat()) return getVehicle(seat);
        return null;
    }

    public Seat getSeat(UUID uuid) {
        return aliveSeatsInteraction.get(uuid);
    }*/

}
