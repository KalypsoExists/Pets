package me.kalypso.vehicles.Vehicles.Objects;

import me.kalypso.vehicles.Vehicles.Vehicle;
import org.jetbrains.annotations.NotNull;

public class Engine extends VehiclePart {

    private final Frame frame;
    private final GearBox gearBox;

    public Engine(@NotNull Vehicle vehicle, @NotNull Frame frame, @NotNull GearBox gearBox) {
        super(vehicle);
        this.frame = frame;
        this.gearBox = gearBox;
    }

    public Frame getFrame() {
        return frame;
    }

}
