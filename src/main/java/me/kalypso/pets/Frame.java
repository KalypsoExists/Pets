package me.kalypso.pets;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public abstract class Frame {

    private ItemDisplay model;

    private ItemStack modelItem;

    private Interaction interaction;

    private ArmorStand ride;

    private Vector3f translation;

    private Vector3f scale;

    private Quaternionf leftRotation;

    private Quaternionf rightRotation;

    private float interactionWidth;

    private float interactionHeight;

    private float currentHealth;

    private final float maxHealth;

    private boolean isAlive;

    public Frame(Vector3f translation, Vector3f scale, Quaternionf leftRotation, Quaternionf rightRotation, float interactionWidth, float interactionHeight, float maxHealth) {
        this.maxHealth = maxHealth;
        this.translation = translation;
        this.scale = scale;
        this.leftRotation = leftRotation;
        this.rightRotation = rightRotation;
        this.interactionWidth = interactionWidth;
        this.interactionHeight = interactionHeight;
        isAlive = true;
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
        this.leftRotation = Utils.getInstance().fromEulerAngles(leftRotation.x, leftRotation.y, leftRotation.z);
    }

    public void setRightRotation_EulerAngle(Vector3f rightRotation) {
        this.rightRotation = Utils.getInstance().fromEulerAngles(rightRotation.x, rightRotation.y, rightRotation.z);
    }

    private double cosE(double value) {
        return Math.cos(value / 2);
    }

    private double sinE(double value) {
        return Math.sin(value / 2);
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
        ride.setGravity(true);
        ride.setInvisible(true);
        ride.setInvulnerable(true);

        ride.addPassenger(model);
        model.addPassenger(interaction);
    }

    // HEALTH HANDLING

    public float getCurrentHealth() {
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
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void repair() {
        currentHealth = maxHealth;
    }

}
