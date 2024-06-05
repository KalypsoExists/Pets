package me.kalypso.vehicles.handler;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Utils;
import me.kalypso.vehicles.vehicles.Car;
import me.kalypso.vehicles.vehicles.Vehicle;
import org.objectweb.asm.TypeReference;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehiclesHandler {

    private final Core core;
    //private static final ListMultimap<String, Vehicle> vehicles = ArrayListMultimap.create();
    //private static final Map<String, Class<? extends Vehicle>> vehicleTypes = new HashMap<>();

    public VehiclesHandler(Core core) {
        this.core = core;
    }

    //public static void registerVehicle(String typeIdentifier, Vehicle vehicle) {
    //    vehicles.put(typeIdentifier, vehicle);
    //}

    //public static void registerType(String typeIdentifier, Class<? extends Vehicle> type) {
    //    vehicleTypes.put(typeIdentifier, type);
    //}

    /*public void loadVehicles() {
        File vehicleFolder = new File(core.getFolder().getAbsolutePath()+File.separator+"vehicles");
        if(!vehicleFolder.exists()) vehicleFolder.mkdir();

        List<File> vehicleTypeFolders = new ArrayList<>();

        File[] folders = vehicleFolder.listFiles();
        if(folders == null) {
            core.getLogger().warning("No vehicles loaded from json.");
            return;
        }
        for(File vehicleTypeFolder : folders) {
            if(vehicleTypeFolder.isDirectory() && vehicleTypes.containsKey(vehicleTypeFolder.getName())) {
                vehicleTypeFolders.add(vehicleTypeFolder);
            }
        }

        if(vehicleTypeFolders.isEmpty()) {
            core.getLogger().warning("No vehicles loaded from json.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        for(File folder : vehicleTypeFolders) {
            File[] vehicles = folder.listFiles();
            if(vehicles == null) continue;
            for(File file : vehicles) {
                if(!file.isFile() | !Utils.getExtension(file).equals("json")) return;
                try {
                    //Reader reader = new FileReader(file);
                    //Vehicle vehicle = core.getGson().fromJson(reader, Vehicle.class);
                    Object list = mapper.readValue(file, vehicleTypes.get(folder.getName()));
                    vehicleTypes.get("car").new

                } catch(FileNotFoundException ex) {
                    core.getLogger().warning("Failed to read file "+file);
                } catch (StreamReadException e) {
                    throw new RuntimeException(e);
                } catch (DatabindException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {
        } catch (SecurityException ex1) {
            core.getLogger().warning("Security exception upon trying to access vehicles folder");
        } catch (NullPointerException ex2) {
            core.getLogger().warning("No vehicles loaded from json");
        } catch (IOException e) {
            core.getLogger().warning("No vehicles loaded from json");
        }
    }*/

}
