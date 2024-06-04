package me.kalypso.vehicles.Vehicles.Objects;

import me.kalypso.vehicles.Vehicles.Vehicle;
import org.jetbrains.annotations.NotNull;

abstract class VehiclePart {

    private final Vehicle vehicle;

    public VehiclePart(@NotNull Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

}
