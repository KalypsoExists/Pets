package me.kalypso.vehicles.Vehicles.Objects;

import org.jetbrains.annotations.NotNull;

public class Engine {

    private final Frame frame;
    private final GearBox gearBox;

    public Engine(@NotNull Frame frame, @NotNull GearBox gearBox) {
        this.frame = frame;
        this.gearBox = gearBox;
    }

    public Frame getFrame() {
        return frame;
    }

}
