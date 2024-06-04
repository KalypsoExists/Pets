package me.kalypso.vehicles.Vehicles;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.*;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Vehicles.Objects.ControlKey;
import me.kalypso.vehicles.Vehicles.Objects.Identity;
import me.kalypso.vehicles.Handler.VehiclesHandler;
import me.kalypso.vehicles.Vehicles.Parts.Frame;
import me.kalypso.vehicles.Vehicles.Parts.Seat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.*;

public abstract class Vehicle extends Identity implements Listener {

    private final Frame chassis;
    private final ListMultimap<String, Frame> parts = ArrayListMultimap.create();
    private final List<UUID> drivers = new ArrayList<>();

    public Vehicle(String name, Frame chassis) {
        setName(name);

        this.chassis = chassis;

        steerVehiclePacket();
        Core.registerEvent(this);

        VehiclesHandler.registerVehicle(this);
    }

    private void syncControls(List<ControlKey> keys) {
        Bukkit.getScheduler().runTask(Core.getInstance(), () -> processControls(keys));
    }

    public abstract void processControls(List<ControlKey> keys);

    public void spawn(World world, Location pos) {
        parts.values().forEach(frame -> frame.spawnFrame(world, pos));
    }

    public void addPart(String partType, Frame frame) {
        parts.put(partType, frame);
    }

    public void addAllParts(String partType, Collection<Frame> frames) {
        parts.putAll(partType, frames);
    }

    public void addMultiParts(ListMultimap<String, Frame> parts) {
        parts.putAll(parts);
    }

    public void addDriver(UUID player) {
        drivers.add(player);
    }

    public void removeDriver(UUID player) {
        drivers.add(player);
    }

    // Listeners

    public void steerVehiclePacket() {
        Core.getInstance().getProtocolManager().addPacketListener(new PacketAdapter(Core.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                PacketContainer packet = e.getPacket();

                /*boolean go = false;
                for(Frame f : parts.get("driver_seat")) {
                    Seat seat = (Seat) f;
                    if(seat.getMountedPassenger().getUniqueId().equals(e.getPlayer().getUniqueId())) go=true;
                }
                if(!go) return;*/

                if(!drivers.contains(e.getPlayer().getUniqueId())) return;

                float ws = packet.getFloat().read(1);
                float ad = packet.getFloat().read(0);
                boolean jump = packet.getBooleans().read(0);
                boolean shift = packet.getBooleans().read(1);

                ArrayList<ControlKey> keys = new ArrayList<>();

                if (ws > 0) keys.add(ControlKey.FORWARD);
                if (ws < 0) keys.add(ControlKey.BACKWARD);
                if (ad < 0) keys.add(ControlKey.RIGHT);
                if (ad > 0) keys.add(ControlKey.LEFT);
                if (jump) keys.add(ControlKey.JUMP);
                if (shift) keys.add(ControlKey.SHIFT);

                if (!keys.isEmpty()) syncControls(keys);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemDrop(PlayerDropItemEvent e) {

        if(!drivers.contains(e.getPlayer().getUniqueId())) return;

        syncControls(List.of(ControlKey.Q));
        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerOpenInventory(InventoryOpenEvent e) {

        if(!drivers.contains(e.getPlayer().getUniqueId())) return;

        syncControls(List.of(ControlKey.E));
        e.setCancelled(true);

    }

}
