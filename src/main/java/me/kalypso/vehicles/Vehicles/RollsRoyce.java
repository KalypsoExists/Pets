package me.kalypso.vehicles.Vehicles;

import me.kalypso.vehicles.Vehicles.Objects.Frame;
import me.kalypso.vehicles.Vehicles.Objects.Gear;
import me.kalypso.vehicles.Vehicles.Objects.GearBox;

import java.util.ArrayList;
import java.util.List;

public class RollsRoyce {
    private Car.Builder builder;

    public RollsRoyce() {
        List<Gear> gears = new ArrayList<>();
        gears.add(new Gear(1));
        gears.add(new Gear(-1));

        Car.Builder rollsRoyce = new Car.Builder("1949 Rolls-Royce Dawn Drophead", new Frame())
                .addBodyPart(new Frame())
                .addSeat(new Frame(), true)
                .addEngine(new Frame(), new GearBox(new Frame(), gears));
    }

    public Vehicle get() {
        return builder.get();
    }
}
