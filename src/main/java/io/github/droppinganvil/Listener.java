package io.github.droppinganvil;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        OfflinePlayer op = Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId());
        if (Core.getInstance().aPlayers.keySet().contains(op)) {return;}
        Core.getInstance().aPlayers.put(op, new APlayer(op, !e.getPlayer().hasPlayedBefore()));
    }
}
