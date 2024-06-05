package me.kalypso.vehicles;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.gson.Gson;
import lombok.Getter;
import me.kalypso.vehicles.handler.CommandHandler;
import me.kalypso.vehicles.handler.RegisteredVehicle;
import me.kalypso.vehicles.handler.VehiclesHandler;
import me.kalypso.vehicles.vehicles.Car;
import me.kalypso.vehicles.vehicles.Vehicle;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class Core extends JavaPlugin {

    @Getter
    private static Core instance;
    @Getter
    private ProtocolManager protocolManager;
    @Getter
    private File folder;
    private final Map<String, RegisteredVehicle<?>> registeredVehicleTypes = new HashMap<>();

    @Override
    public void onEnable() {

        dataFolder();

        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        getCommand("vehicles").setExecutor(new CommandHandler());

        new VehiclesHandler(this);

    }

    @Override
    public void onDisable() {}

    private void dataFolder() {

        if (!getDataFolder().exists()) {
            if(!getDataFolder().mkdir()) {
                getLogger().log(Level.SEVERE, "Disabling plugin, failed to create data folder.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }

        folder = getDataFolder();
        getLogger().log(Level.INFO, "Data folder registered "+folder.getAbsolutePath());

    }

    public void registerEvent(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    public void registerVehicleType(String identifier, RegisteredVehicle<?> vehicleType) {
        registeredVehicleTypes.put(identifier, vehicleType);
    }

}
