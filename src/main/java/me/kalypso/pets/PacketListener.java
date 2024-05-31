package me.kalypso.pets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketListener {

    public PacketListener() {
        steerVehiclePacket();
    }

    public void steerVehiclePacket() {
        Pets.getInstance().getProtocolManager().addPacketListener(new PacketAdapter(Pets.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                float ws = packet.getFloat().read(1);
                float ad = packet.getFloat().read(0);
                boolean jump = packet.getBooleans().read(0);
                boolean shift = packet.getBooleans().read(1);
                UUID id = event.getPlayer().getUniqueId();

                ArrayList<ControlKey> keys = new ArrayList<>();

                if (ws > 0) keys.add(ControlKey.W);
                if (ws < 0) keys.add(ControlKey.S);
                if (ad < 0) keys.add(ControlKey.D);
                if (ad > 0) keys.add(ControlKey.A);
                if (jump) keys.add(ControlKey.JUMP);
                if (shift) keys.add(ControlKey.SHIFT);

                if (!keys.isEmpty()) fire(keys, id);
            }

            public void fire(List<ControlKey> keys, UUID id) {
                Bukkit.getScheduler().runTask(Pets.getInstance(), () -> Bukkit.getPluginManager().callEvent(new ControlKeyEvent(keys, id)));
            }
        });
    }
}
