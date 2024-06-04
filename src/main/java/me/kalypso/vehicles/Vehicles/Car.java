package me.kalypso.vehicles.Vehicles;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import me.kalypso.vehicles.Vehicles.Objects.ControlKey;
import me.kalypso.vehicles.Vehicles.Parts.*;

import java.util.ArrayList;
import java.util.List;

public class Car extends Vehicle {

    public Car(String name, Frame chassis) {
        super(name, chassis);
    }

    @Override
    public void processControls(List<ControlKey> keys) {

    }

    public static class Builder {

        private final Frame chassis;
        private final String name;

        private final ListMultimap<String, Frame> parts = ArrayListMultimap.create();

        public Builder(String name, Frame chassis) {
            this.name = name;
            this.chassis = chassis;
        }

        public Car.Builder addSeat(String name, Frame frame) {
            parts.put("seat", new Seat(name, frame));
            return this;
        }

        public Car.Builder addDriverSeat(String name, Frame frame) {
            parts.put("driver_seat", new Seat(name, frame, true));
            return this;
        }

        public Car.Builder addEngine(String name, Frame frame, GearBox gearBox) {
            parts.put("engine", new Engine(name, frame, gearBox));
            return this;
        }

        public Car.Builder addBodyPart(String name, Frame frame) {
            parts.put("body", new Frame(name, frame));
            return this;
        }

        public Car get() {
            Car car = new Car(name, chassis);
            car.addMultiParts(parts);
            return car;
        }

    }
}
