package me.kalypso.vehicles.vehicles.parts;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.kalypso.vehicles.Core;
import me.kalypso.vehicles.vehicles.objects.Identity;
import me.kalypso.vehicles.handler.InteractionHandler;
import me.kalypso.vehicles.Utils;
import me.kalypso.vehicles.vehicles.objects.Interactable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import me.kalypso.vehicles.vehicles.Vehicle;
import org.joml.Vector3f;

public class Frame extends Identity implements Interactable {

    private final Core core;

    @Getter
    @Setter
    private Vehicle vehicle;

    @Getter
    private ItemDisplay model;
    @Getter
    private Interaction interaction;
    @Getter
    private ArmorStand ride;

    private ItemStack modelItem;

    @Getter
    @Setter
    private Vector3f translation = new Vector3f(0, 0, 0);
    @Getter
    @Setter
    private Quaternionf leftRotation = new Quaternionf(0, 0, 0, 1);
    @Getter
    @Setter
    private Vector3f scale = new Vector3f(0, 0, 0);
    @Getter
    @Setter
    private Quaternionf rightRotation = new Quaternionf(0, 0, 0, 1);

    @Getter
    @Setter
    private float interactionWidth = 1f;
    @Getter
    @Setter
    private float interactionHeight = 1f;

    /*private float currentHealth;
    private final float maxHealth;
    private boolean isAlive;*/

    public Frame(@NotNull Core core, @NotNull String name, @NotNull Vector3f translation, @NotNull Vector3f scale, @NotNull Quaternionf leftRotation, @NotNull Quaternionf rightRotation, float interactionWidth, float interactionHeight) {
        this.core = core;
        this.translation = translation;
        this.scale = scale;
        this.leftRotation = leftRotation;
        this.rightRotation = rightRotation;
        this.interactionWidth = interactionWidth;
        this.interactionHeight = interactionHeight;

        setName(name);
        setupInteraction();

    }

    public Frame(@NotNull Core core, @NotNull String name, @NotNull Frame frame) {
        this(core, name, frame.getTranslation(), frame.getScale(), frame.getLeftRotation(), frame.getRightRotation(), frame.getInteractionWidth(), frame.getInteractionHeight());
    }

    public Frame(@NotNull Core core, @NotNull String name) {
        this.core = core;

        setName(name);
        setupInteraction();
    }

    private void setupInteraction() {
        Interaction i = getInteraction();
        //i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());
        InteractionHandler.registerInteractable(i.getUniqueId(), this);
    }

    // INTERACTION HANDLING

    @Override
    public void onRightClick(Player player) {
    }

    @Override
    public void onLeftClick(Player player) {
    }

    // ITEM DISPLAY

    public void setItemStackModel(ItemStack item) {
        this.modelItem = item;
    }

    // TRANSFORMATION HANDLING

    public void setLeftRotation_EulerAngle(Vector3f leftRotation) {
        this.leftRotation = Utils.fromEulerAngles(leftRotation.x, leftRotation.y, leftRotation.z);
    }

    public void setRightRotation_EulerAngle(Vector3f rightRotation) {
        this.rightRotation = Utils.fromEulerAngles(rightRotation.x, rightRotation.y, rightRotation.z);
    }

    public void updateTransformation() {
        getModel().setTransformation(new Transformation(translation, leftRotation, scale, rightRotation));
    }

    // ENTITY HANDLING

    public void spawnFrame(World world, Location spawnPos) {
        model = (ItemDisplay) world.spawnEntity(spawnPos, EntityType.ITEM_DISPLAY);
        model.setItemStack(modelItem);
        model.setInterpolationDelay(0);
        model.setInterpolationDuration(1);
        Location loc = model.getLocation();
        loc.setPitch(0);
        loc.setYaw(-90);
        model.teleport(loc);
        model.setTransformation(new Transformation(translation, leftRotation, scale, rightRotation));

        interaction = (Interaction) world.spawnEntity(spawnPos, EntityType.INTERACTION);
        interaction.setInteractionWidth(interactionWidth);
        interaction.setInteractionHeight(interactionHeight);
        interaction.setResponsive(false);

        ride = (ArmorStand) world.spawnEntity(spawnPos, EntityType.ARMOR_STAND);
        ride.setSmall(true);
        ride.setGravity(true);
        ride.setInvisible(true);
        ride.setInvulnerable(true);

        ride.addPassenger(model);
        model.addPassenger(interaction);
    }

    public static class Builder {

        private final Core core;

        private String name = "";

        private Vector3f translation = new Vector3f(0, 0, 0);
        private Vector3f scale = new Vector3f(0, 0, 0);
        private Quaternionf leftRotation = new Quaternionf(0, 0, 0, 1);
        private Quaternionf rightRotation = new Quaternionf(0, 0, 0, 1);

        private float interactionHeight = 1f;
        private float interactionWidth = 1f;

        public Builder(Core core) {
            this.core = core;
        }

        public Frame.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Frame.Builder translate(Vector3f translation) {
            this.translation = translation;
            return this;
        }

        public Frame.Builder scale(Vector3f scale) {
            this.scale = scale;
            return this;
        }

        public Frame.Builder leftRotation(Quaternionf leftRotation) {
            this.leftRotation = leftRotation;
            return this;
        }

        public Frame.Builder rightRotation(Quaternionf rightRotation) {
            this.rightRotation = rightRotation;
            return this;
        }

        public Frame.Builder interactionHeight(float height) {
            interactionHeight = height;
            return this;
        }

        public Frame.Builder interactionWidth(float value) {
            interactionWidth = value;
            return this;
        }

        public Frame build() {
            return new Frame(core, name, translation, scale, leftRotation, rightRotation, interactionWidth, interactionHeight);
        }
    }

    // HEALTH HANDLING

    /*public float getCurrentHealth() {
        return currentHealth;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(float health) {
        if (health > maxHealth) {
            currentHealth = maxHealth;
            return;
        } else if (health < 0) {
            currentHealth = 0;
            return;
        }
        currentHealth = health;
    }

    public void heal(float amount) {
        setHealth(currentHealth + amount);
    }

    public void damage(float amount) {
        setHealth(currentHealth - amount);
        if (currentHealth == 0) kill();
    }

    public void kill() {
        interaction.remove();
        model.remove();
        ride.remove();
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void repair() {
        currentHealth = maxHealth;
    }*/

}
