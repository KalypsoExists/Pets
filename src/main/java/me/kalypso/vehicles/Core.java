package me.kalypso.vehicles;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.kalypso.vehicles.Handler.CommandHandler;
import me.kalypso.vehicles.Listeners.KeyListener;
import me.kalypso.vehicles.Listeners.SeatListener;
import me.kalypso.vehicles.Vehicles.RollsRoyce;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    private static Core instance;
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {

        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        getCommand("vehicle").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new KeyListener(), this);
        getServer().getPluginManager().registerEvents(new SeatListener(), this);

        new RollsRoyce();

    }

    @Override
    public void onDisable() {}

    public static Core getInstance() {
        return instance;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }



    /*public boolean isPet(UUID interaction) {
        return runTimePets.get(interaction) != null;
    }

    public boolean isRidingPet(UUID player) {
        return riddenPets.get(player) != null;
    }

    public PetFrame getPet(UUID interaction) {
        return runTimePets.get(interaction);
    }

    public PetFrame getRiddenPet(UUID player) {
        return riddenPets.get(player);
    }

    public void addRunTimePet(UUID interaction, PetFrame pet) {
        runTimePets.put(interaction, pet);
    }

    public void addRiddenPet(UUID player, PetFrame pet) {
        riddenPets.put(player, pet);
    }

    public void removeRiddenPet(UUID player) {
        riddenPets.remove(player);
    }

    public void discardPet(UUID interaction) {
        PetFrame pet = getPet(interaction);
        pet.kill();
        if (pet.isMounted()) removeRiddenPet(pet.getMountedPassenger().getUniqueId());
        runTimePets.remove(interaction);
    }*/


}
