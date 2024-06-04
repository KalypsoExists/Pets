package me.kalypso.vehicles.Vehicles;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.Data.ControlKey;
import me.kalypso.vehicles.Data.Identity;
import me.kalypso.vehicles.Handler.VehiclesHandler;
import me.kalypso.vehicles.Vehicles.Objects.Engine;
import me.kalypso.vehicles.Vehicles.Objects.Frame;
import me.kalypso.vehicles.Vehicles.Objects.Seat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle extends Identity implements Listener {

    private final Frame chassis;
    private final List<Frame> bodyParts;
    private final List<Seat> seats;
    private final List<Engine> engines;
    private final List<Frame> allFrames;

    public Vehicle(String name, Frame chassis, List<Seat> seats, List<Engine> engines, List<Frame> bodyParts) {
        super(name);

        this.chassis = chassis;
        this.seats = seats;
        this.bodyParts = bodyParts;
        this.engines = engines;
        this.allFrames = new ArrayList<>();

        for(Seat seat : seats) {
            allFrames.add(seat.getFrame());
        }
        for(Engine engine : engines) allFrames.add(engine.getFrame());;
        allFrames.addAll(bodyParts);

        steerVehiclePacket();
        Core.registerEvent(this);

        VehiclesHandler.registerVehicle(this);
    }

    private void syncControls(List<ControlKey> keys) {
        Bukkit.getScheduler().runTask(Core.getInstance(), () -> processControls(keys));
    }

    public abstract void processControls(List<ControlKey> keys);

    public void spawn(World world, Location pos) {
        for(Frame frame : allFrames) frame.spawnFrame(world, pos);
    }

    public Seat getSeat(int index) {
        return seats.get(index);
    }

    public void addFrame(Frame frame) {
        allFrames.add(frame);
    }

    public void steerVehiclePacket() {
        Core.getInstance().getProtocolManager().addPacketListener(new PacketAdapter(Core.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                for(Seat seat : seats) if(!seat.isDriverSeat() || !seat.getMountedPassenger().getUniqueId().equals(event.getPlayer().getUniqueId())) return;

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

        for(Seat seat : seats) if(!seat.isDriverSeat() || !seat.getMountedPassenger().getUniqueId().equals(e.getPlayer().getUniqueId())) return;
        syncControls(List.of(ControlKey.Q));
        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerOpenInventory(InventoryOpenEvent e) {

        for(Seat seat : seats) if(!seat.isDriverSeat() || !seat.getMountedPassenger().getUniqueId().equals(e.getPlayer().getUniqueId())) return;
        syncControls(List.of(ControlKey.E));
        e.setCancelled(true);

    }

}
