package me.kalypso.pets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.lang.reflect.Array;
import java.util.*;

public final class Pets extends JavaPlugin {

    private static Pets instance;
    private ProtocolManager protocolManager;
    private final Map<UUID, PetFrame> runTimePets = new HashMap<>(); // Interaction UUID, Pet
    private final Map<UUID, PetFrame> riddenPets = new HashMap<>(); // Player UUID, Pet

    @Override
    public void onEnable() {
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        getCommand("pets").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new PetListener(), this);
        new PacketListener();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Pets getInstance() {
        return instance;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public boolean isPet(UUID interaction) {
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
    }


}
