package me.kalypso.vehicles.vehicles;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.*;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.vehicles.objects.ControlKey;
import me.kalypso.vehicles.vehicles.objects.Identity;
import me.kalypso.vehicles.handler.VehiclesHandler;
import me.kalypso.vehicles.vehicles.parts.Frame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.*;

public abstract class Vehicle extends Identity implements Listener {

    private final Core core;
    private final Frame chassis;
    private final ListMultimap<String, Frame> parts = ArrayListMultimap.create();
    private final List<UUID> drivers = new ArrayList<>();

    public Vehicle(Core core, String name, Frame chassis) {
        setName(name);

        this.core = core;
        this.chassis = chassis;

        steerVehiclePacket();
        core.registerEvent(this);
    }


    private void syncControls(List<ControlKey> keys) {
        Bukkit.getScheduler().runTask(core, () -> processControls(keys));
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
        core.getProtocolManager().addPacketListener(new PacketAdapter(core, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                PacketContainer packet = e.getPacket();

                if(!drivers.contains(e.getPlayer().getUniqueId())) return;

                float forward = packet.getFloat().read(1);
                float sideward = packet.getFloat().read(0);
                boolean jump = packet.getBooleans().read(0);
                boolean shift = packet.getBooleans().read(1);

                ArrayList<ControlKey> keys = new ArrayList<>();

                if (forward > 0) keys.add(ControlKey.FORWARD);
                if (forward < 0) keys.add(ControlKey.BACKWARD);
                if (sideward < 0) keys.add(ControlKey.RIGHT);
                if (sideward > 0) keys.add(ControlKey.LEFT);
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
