package me.kalypso.vehicles;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.kalypso.vehicles.handler.CommandHandler;
import me.kalypso.vehicles.handler.VehiclesHandler;
import me.kalypso.vehicles.vehicles.Car;
import me.kalypso.vehicles.vehicles.Parachute;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public final class Core extends JavaPlugin {

    @Getter
    private static Core instance;
    @Getter
    private ProtocolManager protocolManager;
    @Getter
    private File folder;

    @Override
    public void onEnable() {

        dataFolder();

        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        getCommand("vehicles").setExecutor(new CommandHandler());

        new VehiclesHandler(this);

        VehiclesHandler.registerVehicle("car", new Car(this, null, null));
        VehiclesHandler.registerVehicle("parachute", new Parachute(this, null, null));

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

}
