package me.kalypso.vehicles;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.kalypso.vehicles.handler.CommandHandler;
import me.kalypso.vehicles.vehicles.RollsRoyce;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    @Getter
    private static Core instance;
    @Getter
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {

        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        getCommand("vehicle").setExecutor(new CommandHandler());

        new RollsRoyce(this);

    }

    public void registerEvent(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    @Override
    public void onDisable() {}

}
