package me.kalypso.vehicles.vehicles.objects;

import org.bukkit.entity.Player;

public interface Interactable {

    void onRightClick(Player player);

    void onLeftClick(Player player);

}
