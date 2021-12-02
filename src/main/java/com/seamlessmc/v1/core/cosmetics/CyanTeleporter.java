package com.seamlessmc.v1.core.cosmetics;

import com.seamlessmc.v1.core.APlayer;
import org.bukkit.Effect;
import org.bukkit.Location;

public class CyanTeleporter implements CosmeticEffect {

    public String getName() {
        return "Cyan Teleport";
    }

    public Double getCost() {
        return 0.0;
    }

    public Integer getRequiredLevel() {
        return 0;
    }

    public void deployEffect(APlayer player) {
        Location loc = player.getOfflinePlayer().getPlayer().getLocation();
        int radius = 1;
        for (double y = 0; y <= 50; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            Location locc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
            loc.getWorld().playEffect(locc, Effect.SMOKE, 1);
        }
    }
}
