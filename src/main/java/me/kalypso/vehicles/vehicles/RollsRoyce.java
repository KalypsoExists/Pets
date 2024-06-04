package me.kalypso.vehicles.vehicles;

import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.vehicles.parts.Frame;
import me.kalypso.vehicles.vehicles.parts.GearBox;

public class RollsRoyce {
    private Car.Builder builder;

    public RollsRoyce(Core core) {
        builder = new Car.Builder(core, "1949 Rolls-Royce Dawn Drophead", new Frame.Builder(core).build())
                .addBodyPart(
                        "body",
                        new Frame.Builder(core).build()
                )
                .addDriverSeat(
                        "seat",
                        new Frame.Builder(core).build()
                )
                .addEngine(
                        "engine",
                        new Frame.Builder(core).build(),
                        new GearBox(core, "gearbox", new Frame.Builder(core).build(), new int[]{1, 0})
                );
    }

    public Vehicle get() {
        return builder.build();
    }
}
