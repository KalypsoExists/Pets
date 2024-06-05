package me.kalypso.vehicles.handler;

import me.kalypso.vehicles.vehicles.Vehicle;
import org.json.simple.JSONObject;

@FunctionalInterface
public interface VehicleSerializer<T extends JSONObject> {
    T loadFromVehicle(Vehicle vehicle);
}
