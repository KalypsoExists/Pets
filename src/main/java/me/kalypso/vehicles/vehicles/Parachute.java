package me.kalypso.vehicles.vehicles;

import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.vehicles.objects.ControlKey;
import me.kalypso.vehicles.vehicles.parts.Frame;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;

public class Parachute extends Vehicle {

    public Parachute(Core core, String name, Frame chassis) {
        super(core, name, chassis);
    }

    @Override
    public void processControls(List<ControlKey> keys) {
    }

    @Override
    public void spawn(World world, Location pos) {
        super.spawn(world, pos);
    }

    @Override
    public File serialize(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle deserialize(File json) {
        return null;
    }
}
