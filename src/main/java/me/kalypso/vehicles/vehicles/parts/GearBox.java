package me.kalypso.vehicles.vehicles.parts;

import lombok.Getter;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.vehicles.objects.Interactable;
import org.jetbrains.annotations.NotNull;

@Getter
public class GearBox extends Frame implements Interactable {

    private final int[] gears;

    public GearBox(@NotNull Core core, @NotNull String name, @NotNull Frame frame, int[] gears) {
        super(core, name, frame);
        super.setName(name);

        this.gears = gears;
    }
}
