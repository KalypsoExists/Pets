package me.kalypso.vehicles.Vehicles;

import me.kalypso.vehicles.Data.ControlKey;
import me.kalypso.vehicles.Data.Identity;
import me.kalypso.vehicles.Handler.VehiclesHandler;
import me.kalypso.vehicles.Vehicles.Objects.Engine;
import me.kalypso.vehicles.Vehicles.Objects.Frame;
import me.kalypso.vehicles.Vehicles.Objects.Seat;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle extends Identity {

    private final Frame chassis;
    private final List<Frame> bodyParts;
    private final List<Seat> seats;
    private final List<Engine> engines;
    private final List<Frame> allFrames;

    public Vehicle(String name, Frame chassis, List<Seat> seats, List<Engine> engines, List<Frame> bodyParts) {
        super(name);

        this.chassis = chassis;
        this.seats = seats;
        this.bodyParts = bodyParts;
        this.engines = engines;
        this.allFrames = new ArrayList<>();

        VehiclesHandler.getInstance().addAliveVehicle(this);

        for(Seat seat : seats) {
            VehiclesHandler.getInstance().addAliveSeat(seat, this);
            allFrames.add(seat.getFrame());
        }
        for(Engine engine : engines) allFrames.add(engine.getFrame());;
        allFrames.addAll(bodyParts);

    }

    public abstract void processControls(List<ControlKey> keys);

    public void spawn(World world, Location pos) {
        for(Frame frame : allFrames) frame.spawnFrame(world, pos);
    }

    public Seat getSeat(int index) {
        return seats.get(index);
    }

    public void addFrame(Frame frame) {
        allFrames.add(frame);
    }
}
