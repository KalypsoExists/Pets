package me.kalypso.vehicles.Vehicles.Objects;

import me.kalypso.vehicles.Handler.InteractionHandler;
import me.kalypso.vehicles.Utils;
import me.kalypso.vehicles.Handler.VehiclesHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import me.kalypso.vehicles.Vehicles.Vehicle;
import org.joml.Vector3f;

public class Frame extends VehiclePart implements Interactable {

    private ItemDisplay model;
    private Interaction interaction;
    private ArmorStand ride;

    private ItemStack modelItem;

    private Vector3f translation;
    private Quaternionf leftRotation;
    private Vector3f scale;
    private Quaternionf rightRotation;

    private float interactionWidth;
    private float interactionHeight;

    /*private float currentHealth;
    private final float maxHealth;
    private boolean isAlive;*/

    public Frame(@NotNull Vehicle vehicle, @NotNull Vector3f translation, @NotNull Vector3f scale, @NotNull Quaternionf leftRotation, @NotNull Quaternionf rightRotation, float interactionWidth, float interactionHeight) {

        super(vehicle);

        this.translation = translation;
        this.scale = scale;
        this.leftRotation = leftRotation;
        this.rightRotation = rightRotation;
        this.interactionWidth = interactionWidth;
        this.interactionHeight = interactionHeight;

        setupInteraction();

    }

    public Frame(@NotNull Vehicle vehicle) {

        super(vehicle);

        this.translation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(0, 0, 0);
        this.leftRotation = new Quaternionf(0, 0, 0, 1);
        this.rightRotation = new Quaternionf(0, 0, 0, 1);
        this.interactionWidth = 1;
        this.interactionHeight = 1;

        setupInteraction();

    }

    private void setupInteraction() {
        Interaction i = getInteraction();
        i.getPersistentDataContainer().set(VehiclesHandler.key, PersistentDataType.STRING, getVehicle().getId().toString());

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

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setLeftRotation(Quaternionf leftRotation) {
        this.leftRotation = leftRotation;
    }

    public void setRightRotation(Quaternionf rightRotation) {
        this.rightRotation = rightRotation;
    }

    public void setLeftRotation_EulerAngle(Vector3f leftRotation) {
        this.leftRotation = Utils.fromEulerAngles(leftRotation.x, leftRotation.y, leftRotation.z);
    }

    public void setRightRotation_EulerAngle(Vector3f rightRotation) {
        this.rightRotation = Utils.fromEulerAngles(rightRotation.x, rightRotation.y, rightRotation.z);
    }

    public void updateTransformation() {
        getModel().setTransformation(new Transformation(translation, leftRotation, scale, rightRotation));
    }

    public void setInteractionWidth(float interactionWidth) {
        this.interactionWidth = interactionWidth;
    }

    public void setInteractionHeight(float interactionHeight) {
        this.interactionHeight = interactionHeight;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Quaternionf getLeftRotation() {
        return leftRotation;
    }

    public Quaternionf getRightRotation() {
        return rightRotation;
    }

    public float getInteractionHeight() {
        return interactionHeight;
    }

    public float getInteractionWidth() {
        return interactionWidth;
    }

    // ENTITY HANDLING

    public ItemDisplay getModel() {
        return model;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public ArmorStand getRide() {
        return ride;
    }

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
