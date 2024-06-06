package me.kalypso.vehicles.handler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Utils;
import me.kalypso.vehicles.vehicles.Vehicle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehiclesHandler {

    private final Core core;
    private static final Multimap<String, Vehicle> registeredVehicles = ArrayListMultimap.create(); // Rolls Royce
    private static final Map<String, Vehicle> registeredVehicleTypes = new HashMap(); // Car

    public VehiclesHandler(Core core) {
        this.core = core;
    }

    public static void registerVehicle(String identifier, Vehicle vehicle) {
        registeredVehicles.put(identifier, vehicle);
    }

    public void loadVehicles() {

        // plugin/vehicles
        File vehicleFolder = new File(core.getFolder().getAbsolutePath()+File.separator+"vehicles");
        if(!vehicleFolder.exists()) vehicleFolder.mkdir(); // create it if it doesnt exist

        // plugin/vehicles/<type>(s)
        List<File> vehicleTypeFolders = new ArrayList<>();

        // Get all the possible vehicle type folders then add the valid ones to the list
        File[] folders = vehicleFolder.listFiles();
        if(folders == null) {
            core.getLogger().warning("No vehicles loaded from json.");
            return;
        }
        for(File vehicleTypeFolder : folders) {
            // Must be directory and must be a valid type
            if(vehicleTypeFolder.isDirectory() && registeredVehicleTypes.containsKey(vehicleTypeFolder.getName())) {
                vehicleTypeFolders.add(vehicleTypeFolder);
            }
        }

        // No vehicle types found that means nothing to load
        if(vehicleTypeFolders.isEmpty()) {
            core.getLogger().warning("No vehicles loaded from json.");
            return;
        }

        // Now loading every vehicle json file0
        for(File folder : vehicleTypeFolders) {
            File[] vehicles = folder.listFiles();
            if(vehicles == null) continue;
            for(File file : vehicles) {
                if(!file.isFile() | !Utils.getExtension(file).equals("json")) return;
                Vehicle vehicle = registeredVehicleTypes.get(folder.getName()).deserialize(file);
                registerVehicle(folder.getName(), vehicle);
            }
        }
    }

}
