package me.kalypso.pets;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.util.Vector;

import java.util.*;

public class PetListener implements Listener {
    @EventHandler
    public void onPetInteract(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (!(e.getRightClicked() instanceof Interaction i)) return;

        PetFrame pet = Pets.getInstance().getPet(i.getUniqueId());
        if (pet == null) return;

        pet.mountPassenger(p, false);
        Pets.getInstance().addRiddenPet(p.getUniqueId(), pet);

        /*if (e.getAction().isRightClick()) {

        } else if (e.getAction().isLeftClick()) {
            for (PetFrame pet : Pets.getInstance().getRunTimePets()) {
                if (pet.getInteraction().getUniqueId().equals(interaction)) {
                    List<Double> amounts = new ArrayList<>();
                    Collection<AttributeModifier> modifiers = p.getActiveItem().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE);
                    if (modifiers != null) {
                        for (AttributeModifier mod : modifiers) amounts.add(mod.getAmount());
                    }
                    p.sendMessage(Arrays.toString(amounts.toArray(new Double[0])));
                }
            }
        }*/
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (Pets.getInstance().isRidingPet(p.getUniqueId())) return;

        e.setCancelled(true);

        List<ControlKey> keys = new ArrayList<>();
        keys.add(ControlKey.Q);

        Bukkit.getPluginManager().callEvent(new ControlKeyEvent(keys, p.getUniqueId()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerSprint(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();

        if (Pets.getInstance().isRidingPet(p.getUniqueId())) return;

        e.setCancelled(true);

        List<ControlKey> keys = new ArrayList<>();
        keys.add(ControlKey.CTRL);

        Bukkit.getPluginManager().callEvent(new ControlKeyEvent(keys, p.getUniqueId()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerOpenInventory(InventoryOpenEvent e) {
        HumanEntity p = e.getPlayer();

        if (Pets.getInstance().isRidingPet(p.getUniqueId())) return;

        e.setCancelled(true);

        List<ControlKey> keys = new ArrayList<>();
        keys.add(ControlKey.E);

        Bukkit.getPluginManager().callEvent(new ControlKeyEvent(keys, p.getUniqueId()));
    }

    @EventHandler
    public void onPetHit(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Interaction i)) return;
        if (!(e.getDamager() instanceof Player p)) return;

        if (Pets.getInstance().isPet(i.getUniqueId())) {
            Pets.getInstance().discardPet(i.getUniqueId());
        }

    }

    @EventHandler
    public void onControlKeyTrigger(ControlKeyEvent e) {

        Objects.requireNonNull(Bukkit.getPlayer(e.getPlayerUUID()))
                .sendActionBar(Component.text(Arrays.toString(e.getKeys().toArray())));

        if (e.getKeys().contains(ControlKey.JUMP)) {

            PetFrame pet = Pets.getInstance().getRiddenPet(e.getPlayerUUID());
            if (pet == null) return;

            ArmorStand ride = pet.getRide();

            ride.setVelocity(ride.getVelocity().add(new Vector(0, 3, 0)));

        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPetMount(EntityDismountEvent e) {

        if (!(e.getDismounted() instanceof Interaction i)) return;
        if (!(e.getEntity() instanceof Player p)) return;

        PetFrame pet = Pets.getInstance().getPet(i.getUniqueId());
        if (pet == null) return;

        if (pet.isMounted()) e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player p = e.getPlayer();

        PetFrame pet = Pets.getInstance().getRiddenPet(p.getUniqueId());
        if (pet != null) pet.dismountPassenger();

    }
}
