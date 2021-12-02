package com.seamlessmc.v1.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onGUI(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {return;}
        if (!(e.getClickedInventory().getHolder() instanceof Menu)) {return;}
        e.setCancelled(true);
        ((Menu) e.getClickedInventory().getHolder()).onClick(e.getRawSlot(), e.getClick());
    }
}
