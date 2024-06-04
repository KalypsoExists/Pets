package me.kalypso.vehicles.Vehicles;

import me.kalypso.vehicles.Vehicles.Parts.Frame;
import me.kalypso.vehicles.Vehicles.Parts.GearBox;

import java.util.ArrayList;
import java.util.List;

public class RollsRoyce {
    private Car.Builder builder;

    public RollsRoyce() {
        builder = new Car.Builder("1949 Rolls-Royce Dawn Drophead", new Frame("chassis", Frame.defaultFrame()))
                .addBodyPart("body", Frame.defaultFrame())
                .addDriverSeat("seat", Frame.defaultFrame())
                .addEngine("engine", Frame.defaultFrame(), new GearBox("gearbox", Frame.defaultFrame(), new int[]{1, 0}));
    }

    public Vehicle get() {
        return builder.get();
    }
}
