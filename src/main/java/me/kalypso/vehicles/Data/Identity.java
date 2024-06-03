package me.kalypso.vehicles.Data;

import java.util.UUID;

public class Identity {

    private String name;
    private final UUID id;

    public Identity(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
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
