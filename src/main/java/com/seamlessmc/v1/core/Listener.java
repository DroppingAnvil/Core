package com.seamlessmc.v1.core;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        OfflinePlayer op = Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId());
        if (SpigotHook.getInstance().aPlayers.keySet().contains(op)) {return;}
        SpigotHook.getInstance().aPlayers.put(op, new APlayer(op, !e.getPlayer().hasPlayedBefore()));
    }
}
