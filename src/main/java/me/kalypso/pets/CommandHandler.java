package me.kalypso.pets;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    List<String> options = new ArrayList<>();
    List<String> editSubOptions = new ArrayList<>();

    CommandHandler() {
        options.add("spawn");
        options.add("dismount");
        options.add("edit");

        editSubOptions.add("translation");
        editSubOptions.add("scale");
        editSubOptions.add("left_rotation");
        editSubOptions.add("right_rotation");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player p)) return false;

        if (command.getName().equalsIgnoreCase("pets")) {
            if (args[0].equals("spawn")) {

                PetFrame pet = getPetFrame();
                pet.spawnFrame(p.getWorld(), p.getLocation().add(p.getLocation().getDirection().normalize()));

                Pets.getInstance().addRunTimePet(pet.getInteraction().getUniqueId(), pet);

            } else if (args[0].equals("dismount")) {


                PetFrame pet = Pets.getInstance().getRiddenPet(p.getUniqueId());
                if (pet == null) return false;

                pet.dismountPassenger();
                Pets.getInstance().removeRiddenPet(p.getUniqueId());

            } else if (args[0].equals("edit")) {

                PetFrame pet = Pets.getInstance().getRiddenPet(p.getUniqueId());
                if (pet == null) return false;

                float x, y, z;

                try {
                    x = Float.parseFloat(args[2]);
                    y = Float.parseFloat(args[3]);
                    z = Float.parseFloat(args[4]);
                } catch (Exception e) {
                    return false;
                }

                switch (args[1].toLowerCase()) {
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
                }

                pet.updateTransformation();

            }
        }

        return true;

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


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player p)) return null;

        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("pets")) {

            if (args.length == 0) return completions;
            if (args.length == 1) StringUtil.copyPartialMatches(args[0], options, completions);
            if (args.length == 2) {
                if (args[0].equals("edit")) StringUtil.copyPartialMatches(args[1], editSubOptions, completions);
            }
            if (args.length == 3) {
                if (editSubOptions.contains(args[1])) completions.add("x");
            }

            if (args.length == 4) {
                if (editSubOptions.contains(args[1])) completions.add("y");
            }

            if (args.length == 5) {
                if (editSubOptions.contains(args[1])) completions.add("z");
            }

            Collections.sort(completions);
            return completions;
        }
        return null;
    }
}
