package me.kalypso.vehicles.Vehicles.Parts;

import org.jetbrains.annotations.NotNull;

public class GearBox extends Frame {

    private final int[] gears;

    public GearBox(@NotNull String name, @NotNull Frame frame, int[] gears) {
        super(frame);
        super.setName(name);

        this.gears = gears;
    }

    public int[] getGears() {
        return gears;
    }
}
