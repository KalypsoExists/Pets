package me.kalypso.vehicles.Handler;

import me.kalypso.vehicles.Vehicles.Objects.Frame;
import me.kalypso.vehicles.Vehicles.Objects.Seat;
import me.kalypso.vehicles.Vehicles.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VehiclesHandler {

    private static VehiclesHandler instance;

    private final Map<UUID, Vehicle> aliveVehicles = new HashMap<>();
    private final Map<UUID, Frame> aliveFrames = new HashMap<>();
    private final Map<Seat, Vehicle> aliveSeatsVehicle = new HashMap<>();
    private final Map<UUID, Seat> aliveSeatsInteraction = new HashMap<>();
    private final Map<UUID, Seat> riddenSeats = new HashMap<>();

    public VehiclesHandler() {
        instance = this;
    }

    public static VehiclesHandler getInstance() {
        return instance;
    }
    // MAP HANDLING

    public void addAliveVehicle(Vehicle vehicle) {
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
    }

}
