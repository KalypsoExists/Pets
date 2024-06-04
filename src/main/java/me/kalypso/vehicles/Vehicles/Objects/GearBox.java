package me.kalypso.vehicles.Vehicles.Objects;

import me.kalypso.vehicles.Vehicles.Vehicle;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GearBox extends VehiclePart {

    private final Frame frame;
    private final List<Gear> gears;

    public GearBox(@NotNull Vehicle vehicle, @NotNull Frame frame, @NotNull List<Gear> gears) {
        super(vehicle);
        this.frame = frame;
        this.gears = gears;
    }
}
