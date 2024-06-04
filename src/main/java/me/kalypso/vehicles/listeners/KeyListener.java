package me.kalypso.vehicles.listeners;

import org.bukkit.event.Listener;

public class KeyListener implements Listener {

    /*private boolean Q = false, E = false;

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

                if (ws > 0) keys.add(ControlKey.FORWARD);
                if (ws < 0) keys.add(ControlKey.BACKWARD);
                if (ad < 0) keys.add(ControlKey.RIGHT);
                if (ad > 0) keys.add(ControlKey.LEFT);
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
    }*/

}
