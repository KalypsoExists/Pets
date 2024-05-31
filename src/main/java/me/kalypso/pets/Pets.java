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

public final class Pets extends JavaPlugin implements CommandExecutor, TabCompleter {

    private static Pets instance;
    private ProtocolManager protocolManager;
    private final Map<UUID, PetFrame> runTimePets = new HashMap<>(); // Interaction UUID, Pet
    private final Map<UUID, PetFrame> riddenPets = new HashMap<>(); // Player UUID, Pet

    List<String> options = new ArrayList<>();


    List<String> edit_subOptions = new ArrayList<>();


    @Override
    public void onEnable() {
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        getCommand("pets").setExecutor(this);
        tabComplete();
        getServer().getPluginManager().registerEvents(new PetListener(), this);
        steerVehiclePacket();
    }

    private void tabComplete() {
        options.add("spawn");
        options.add("dismount");
        options.add("edit");

        edit_subOptions.add("translation");
        edit_subOptions.add("scale");
        edit_subOptions.add("left_rotation");
        edit_subOptions.add("right_rotation");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void steerVehiclePacket() {
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                float ws = packet.getFloat().read(1);
                float ad = packet.getFloat().read(0);
                boolean jump = packet.getBooleans().read(0);
                boolean shift = packet.getBooleans().read(1);
                UUID id = event.getPlayer().getUniqueId();

                ArrayList<ControlKey> keys = new ArrayList<>();

                if(ws > 0) keys.add(ControlKey.W);
                if(ws < 0) keys.add(ControlKey.S);
                if(ad < 0) keys.add(ControlKey.D);
                if(ad > 0) keys.add(ControlKey.A);
                if(jump) keys.add(ControlKey.JUMP);
                if(shift) keys.add(ControlKey.SHIFT);

                if(!keys.isEmpty()) fire(keys, id);
            }

            public void fire(List<ControlKey> keys, UUID id) {
                Bukkit.getScheduler().runTask(Pets.getInstance(), () -> Bukkit.getPluginManager().callEvent(new ControlKeyEvent(keys, id)));
            }
        });
    }

    public static Pets getInstance() {
        return instance;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    private float[] stringToFloatArray(String[] array) {
        float[] floatArray = new float[array.length];

        try {
            int i = 0;
            for (String f : array) {
                floatArray[i++] = Float.parseFloat(f);
            }
        } catch (Exception ex) {
            return null;
        }

        return floatArray;
    }

    private static PetFrame getPetFrame() {
        PetFrame pet = new PetFrame(new Vector3f(0f, 0f, 0f),
                new Vector3f(1f, 1f, 1f),
                new Quaternionf(0f, 0f, 0f, 1f),
                new Quaternionf(0f, 0f, 0f, 1f),
                1f,
                1f,
                10);

        ItemStack item = new ItemStack(Material.RABBIT_HIDE);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(2);
        item.setItemMeta(meta);

        pet.setItemStackModel(item);
        return pet;
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
        if(pet.isMounted()) removeRiddenPet(pet.getMountedPassenger().getUniqueId());
        runTimePets.remove(interaction);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player p)) return false;

        if(command.getName().equalsIgnoreCase("pets")) {
            if(args[0].equals("spawn")){

                PetFrame pet = getPetFrame();
                pet.spawnFrame(p.getWorld(), p.getLocation().add(p.getLocation().getDirection().normalize()));

                addRunTimePet(pet.getInteraction().getUniqueId(), pet);

            } else if(args[0].equals("dismount")){


                PetFrame pet = getRiddenPet(p.getUniqueId());
                if(pet==null) return false;

                pet.dismountPassenger();
                removeRiddenPet(p.getUniqueId());

            } else if(args[0].equals("edit")){

                PetFrame pet = getRiddenPet(p.getUniqueId());
                if(pet==null) return false;

                float x, y, z;

                try {
                    x = Float.parseFloat(args[2]);
                    y = Float.parseFloat(args[3]);
                    z = Float.parseFloat(args[4]);
                } catch (Exception e) { return false; }

                switch (args[1]) {
                    case "translation" -> {
                        pet.setTranslation(new Vector3f(x, y, z));
                        p.sendMessage("New translation " + x + " " + y + " " + z);
                    }
                    case "scale" -> {
                        pet.setScale(new Vector3f(x, y, z));
                        p.sendMessage("New scale " + x + " " + y + " " + z);
                    }
                    case "left_rotation" -> {
                        pet.setLeftRotation_EulerAngle(new Vector3f(x, y, z));
                        p.sendMessage("New left rotation " + x + " " + y + " " + z);
                    }
                    case "right_rotation" -> {
                        pet.setRightRotation_EulerAngle(new Vector3f(x, y, z));
                        p.sendMessage("New right rotation " + x + " " + y + " " + z);
                    }
                    default -> {
                        pet.updateTransformation();
                    }
                }



            }
        }

        return true;

    }



    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(!(sender instanceof Player p)) return null;

        List<String> completions = new ArrayList<>();

        if(command.getName().equalsIgnoreCase("pets")) {

            if(args.length == 0) return completions;
            if(args.length == 1) StringUtil.copyPartialMatches(args[0], options, completions);
            if(args.length == 2) {
                if(args[0].equals("edit")) StringUtil.copyPartialMatches(args[1], edit_subOptions, completions);
            }
            if(args.length == 3) {
                if(edit_subOptions.contains(args[1])) completions.add("x");
            }

            if(args.length == 4) {
                if(edit_subOptions.contains(args[1])) completions.add("y");
            }

            if(args.length == 5) {
                if(edit_subOptions.contains(args[1])) completions.add("z");
            }

            Collections.sort(completions);
            return completions;
        }
        return null;
    }
}
