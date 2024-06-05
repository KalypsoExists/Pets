package me.kalypso.vehicles.handler;

import me.kalypso.vehicles.vehicles.Vehicle;
import org.json.simple.JSONObject;

public interface VehicleDeserializer<T extends Vehicle> {
    T loadFromJson(JSONObject json);
}
