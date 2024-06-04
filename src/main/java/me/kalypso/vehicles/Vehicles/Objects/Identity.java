package me.kalypso.vehicles.Vehicles.Objects;

import java.util.UUID;

public class Identity {

    private String name;
    private final UUID id;

    public Identity() {
        this.id = UUID.randomUUID();
        name = id.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}
