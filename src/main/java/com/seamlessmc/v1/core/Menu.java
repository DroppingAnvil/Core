package com.seamlessmc.v1.core;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;

public interface Menu extends InventoryHolder {
    void onClick(int slot, ClickType ctype);
    void build();
}
