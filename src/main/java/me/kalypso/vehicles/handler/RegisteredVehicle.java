package me.kalypso.vehicles.handler;

import me.kalypso.vehicles.vehicles.Vehicle;
import org.json.simple.JSONObject;

public class RegisteredVehicle<T extends Vehicle, U extends JSONObject> {
    VehicleSerializer<U> serializer;
    VehicleDeserializer<T> deserializer;
}
