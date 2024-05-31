package me.kalypso.pets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

public class PetFrame extends Frame {

    private boolean mounted;

    public PetFrame(Vector3f translation, Vector3f scale, Quaternionf leftRotation, Quaternionf rightRotation, float interactionWidth, float interactionHeight, float maxHealth) {
        super(translation, scale, leftRotation, rightRotation, interactionWidth, interactionHeight, maxHealth);
    }

    public boolean isMounted() {
        return mounted;
    }

    public void mountPassenger(Player passenger, boolean force) {

        if (!getInteraction().getPassengers().isEmpty()) {
            if (!force) return;
            emptyPassengers();
        }

        getInteraction().addPassenger(passenger);

        mounted = true;

    }

    public void dismountPassenger() {
        emptyPassengers();
    }

    private void emptyPassengers() {

        for (Entity e : getInteraction().getPassengers())
            getInteraction().removePassenger(e);

        mounted = false;

    }

    public Entity getMountedPassenger() {

        for (Entity e : getInteraction().getPassengers()) {
            if (e instanceof Player) {
                return (Player) e;
            }
        }

        return null;

    }

}
