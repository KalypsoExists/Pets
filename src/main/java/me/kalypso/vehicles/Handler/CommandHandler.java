package me.kalypso.vehicles.Handler;

import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Vehicles.Objects.Frame;
import me.kalypso.vehicles.Vehicles.Objects.Seat;
import me.kalypso.vehicles.Vehicles.RollsRoyce;
import me.kalypso.vehicles.Vehicles.Vehicle;
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
    List<String> editTransformationOptions = new ArrayList<>();

    CommandHandler() {
        options.add("spawn");
        options.add("dismount");
        options.add("edit");

        editSubOptions.add("seat");

        editTransformationOptions.add("translation");
        editTransformationOptions.add("scale");
        editTransformationOptions.add("left_rotation");
        editTransformationOptions.add("right_rotation");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player p)) return false;

        if (command.getName().equalsIgnoreCase("vehicles")) {
            if (args[0].equals("spawn")) {
                Vehicle vehicle = new RollsRoyce().get();
                vehicle.spawn(p.getWorld(), p.getLocation().add(p.getLocation().getDirection().normalize()));

            } else if (args[0].equals("dismount")) {
                Seat seat = VehiclesHandler.getInstance().getSeat(p.getUniqueId());
                seat.dismountPassenger(p);


            } else if (args[0].equals("edit")) {

                Vehicle vehicle = VehiclesHandler.getInstance().getVehicleIfDriver(p.getUniqueId());
                if (vehicle == null) return false;

                float x, y, z;

                try {
                    x = Float.parseFloat(args[3]);
                    y = Float.parseFloat(args[4]);
                    z = Float.parseFloat(args[5]);
                } catch (Exception e) {
                    return false;
                }

                if(args[1].equalsIgnoreCase("seat")) {

                    Frame frame = vehicle.getSeat(0).getFrame();

                    switch (args[2].toLowerCase()) {
                        case "translation" -> {
                            frame.setTranslation(new Vector3f(x, y, z));
                            p.sendMessage("[" +x+", "+y+", "+z+"]");
                        }
                        case "scale" -> {
                            frame.setScale(new Vector3f(x, y, z));
                            p.sendMessage("[" +x+", "+y+", "+z+"]");
                        }
                        case "left_rotation" -> {
                            frame.setLeftRotation_EulerAngle(new Vector3f(x, y, z));
                            p.sendMessage("[" +x+", "+y+", "+z+"]");
                        }
                        case "right_rotation" -> {
                            frame.setRightRotation_EulerAngle(new Vector3f(x, y, z));
                            p.sendMessage("[" +x+", "+y+", "+z+"]");
                        }
                    }

                    frame.updateTransformation();
                }

            }
        }

        return true;

    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player p)) return null;

        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("vehicles")) {

            if (args.length == 0) return completions;
            if (args.length == 1) StringUtil.copyPartialMatches(args[0], options, completions);
            if (args.length == 2) {
                if (args[0].equals("edit")) StringUtil.copyPartialMatches(args[1], editSubOptions, completions);
            }
            if (args.length == 3) {
                if (editSubOptions.contains(args[1])) StringUtil.copyPartialMatches(args[1], editTransformationOptions, completions);
            }

            // XYZ
            if (args.length == 4) {
                if (editTransformationOptions.contains(args[2])) completions.add("x");
            }
            if (args.length == 5) {
                if (editTransformationOptions.contains(args[2])) completions.add("y");
            }
            if (args.length == 6) {
                if (editTransformationOptions.contains(args[2])) completions.add("z");
            }

            Collections.sort(completions);
            return completions;
        }
        return null;
    }
}
