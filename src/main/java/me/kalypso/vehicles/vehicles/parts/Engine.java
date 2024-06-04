package me.kalypso.vehicles.vehicles.parts;

import lombok.Getter;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.handler.InteractionHandler;
import me.kalypso.vehicles.vehicles.objects.Interactable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Engine extends Frame implements Interactable {

    private final Core core;
    @Getter
    private final GearBox gearBox;

    public Engine(Core core, String name, @NotNull Frame frame, @NotNull GearBox gearBox) {
        super(core, name, frame);

        this.core = core;
        this.gearBox = gearBox;

        //i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());
        //Core.registerEvent(this);

        InteractionHandler.registerInteractable(getInteraction().getUniqueId(), this);
    }

    @Override
    public void onRightClick(Player player) {}

    @Override
    public void onLeftClick(Player player) {}

}
