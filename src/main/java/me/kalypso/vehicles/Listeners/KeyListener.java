package me.kalypso.vehicles.Listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.kalypso.vehicles.Data.ControlKey;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Handler.VehiclesHandler;
import me.kalypso.vehicles.Vehicles.Vehicle;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.List;

public class KeyListener implements Listener {

    private boolean Q = false, E = false;

    public KeyListener() {
        steerVehiclePacket();
    }

    public void steerVehiclePacket() {
        Core.getInstance().getProtocolManager().addPacketListener(new PacketAdapter(Core.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                Vehicle vehicle = VehiclesHandler.getInstance().getVehicleIfDriver(event.getPlayer().getUniqueId());
                if(vehicle == null) return;

                float ws = packet.getFloat().read(1);
                float ad = packet.getFloat().read(0);
                boolean jump = packet.getBooleans().read(0);
                boolean shift = packet.getBooleans().read(1);

                ArrayList<ControlKey> keys = new ArrayList<>();

                if (ws > 0) keys.add(ControlKey.W);
                if (ws < 0) keys.add(ControlKey.S);
                if (ad < 0) keys.add(ControlKey.D);
                if (ad > 0) keys.add(ControlKey.A);
                if (jump) keys.add(ControlKey.JUMP);
                if (shift) keys.add(ControlKey.SHIFT);

                if (!keys.isEmpty()) Bukkit.getScheduler().runTask(Core.getInstance(), () -> {
                    vehicle.processControls(keys);
                });;
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        Vehicle vehicle = VehiclesHandler.getInstance().getVehicleIfDriver(p.getUniqueId());
        if (vehicle == null) return;

        vehicle.processControls(List.of(ControlKey.Q));

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerOpenInventory(InventoryOpenEvent e) {
        HumanEntity p = e.getPlayer();

        Vehicle vehicle = VehiclesHandler.getInstance().getVehicleIfDriver(p.getUniqueId());
        if (vehicle == null) return;

        vehicle.processControls(List.of(ControlKey.E));

        e.setCancelled(true);
    }

}
