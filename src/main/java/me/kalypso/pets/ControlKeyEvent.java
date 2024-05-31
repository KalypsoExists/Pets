package me.kalypso.pets;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;
import java.util.UUID;

public class ControlKeyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();

    private final List<ControlKey> keys;
    private final UUID playerUUID;

    protected ControlKeyEvent(List<ControlKey> keys, UUID playerUUID) {
        this.keys = keys;
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public List<ControlKey> getKeys() {
        return keys;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
