package me.kalypso.vehicles.Vehicles.Parts;

import me.kalypso.vehicles.Handler.InteractionHandler;
import me.kalypso.vehicles.Vehicles.Objects.Interactable;
import me.kalypso.vehicles.Vehicles.Vehicle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Engine extends Frame implements Interactable {

    private final GearBox gearBox;

    public Engine(String name, @NotNull Frame frame, @NotNull GearBox gearBox) {
        super(frame);

        this.gearBox = gearBox;

        //i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());
        //Core.registerEvent(this);

        InteractionHandler.registerInteractable(getInteraction().getUniqueId(), this);
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    @Override
    public void onRightClick(Player player) {}

    @Override
    public void onLeftClick(Player player) {}

}
