package me.kalypso.vehicles.Vehicles.Objects;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GearBox {

    private final Frame frame;
    private final List<Gear> gears;

    public GearBox(@NotNull Frame frame, @NotNull List<Gear> gears) {
        this.frame = frame;
        this.gears = gears;
    }
}
