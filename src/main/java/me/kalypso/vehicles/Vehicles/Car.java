package me.kalypso.vehicles.Vehicles;

import me.kalypso.vehicles.Data.ControlKey;
import me.kalypso.vehicles.Handler.VehiclesHandler;
import me.kalypso.vehicles.Vehicles.Objects.Engine;
import me.kalypso.vehicles.Vehicles.Objects.Frame;
import me.kalypso.vehicles.Vehicles.Objects.GearBox;
import me.kalypso.vehicles.Vehicles.Objects.Seat;

import java.util.ArrayList;
import java.util.List;

public class Car extends Vehicle {

    public Car(String name, Frame chassis, List<Seat> seats, List<Engine> engines, List<Frame> bodyParts) {
        super(name, chassis, seats, engines, bodyParts);
    }

    @Override
    public void processControls(List<ControlKey> keys) {

    }

    public static class Builder {

        private final Frame chassis;
        private final String name;

        private final List<Frame> bodyParts = new ArrayList<>();
        private final List<Seat> seats = new ArrayList<>();
        private final List<Engine> engines = new ArrayList<>();

        public Builder(String name, Frame chassis) {
            this.name = name;
            this.chassis = chassis;
        }

        public Car.Builder addSeat(Frame frame, boolean driverSeat) {
            seats.add(new Seat(frame, driverSeat));
            return this;
        }

        public Car.Builder addEngine(Frame frame, GearBox gearBox) {
            engines.add(new Engine(frame, gearBox));
            return this;
        }

        public Car.Builder addBodyPart(Frame frame) {
            bodyParts.add(frame);
            return this;
        }

        public Car get() {
            return new Car(name, chassis, seats, engines, bodyParts);
        }

    }
}
