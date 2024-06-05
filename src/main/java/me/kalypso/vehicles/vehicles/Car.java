package me.kalypso.vehicles.vehicles;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.vehicles.objects.ControlKey;
import me.kalypso.vehicles.vehicles.parts.*;
import org.json.simple.JSONObject;

import java.util.List;

public class Car extends Vehicle {

    public Car(Core core, String name, Frame chassis) {
        super(core, name, chassis);
    }

    @Override
    public void processControls(List<ControlKey> keys) {

    }

    public static class Builder {

        private final Frame chassis;
        private final String name;
        private final Core core;

        private final ListMultimap<String, Frame> parts = ArrayListMultimap.create();

        public Builder(Core core, String name, Frame chassis) {
            this.core = core;
            this.name = name;
            this.chassis = chassis;
        }

        public Car.Builder addSeat(String name, Frame frame) {
            parts.put("seat", new Seat(core, name, frame));
            return this;
        }

        public Car.Builder addDriverSeat(String name, Frame frame) {
            parts.put("driver_seat", new Seat(core, name, frame, true));
            return this;
        }

        public Car.Builder addEngine(String name, Frame frame, GearBox gearBox) {
            parts.put("engine", new Engine(core, name, frame, gearBox));
            return this;
        }

        public Car.Builder addBodyPart(String name, Frame frame) {
            parts.put("body", new Frame(core, name, frame));
            return this;
        }

        public Car build() {
            Car car = new Car(core, name, chassis);
            car.addMultiParts(parts);
            return car;
        }

    }
}
