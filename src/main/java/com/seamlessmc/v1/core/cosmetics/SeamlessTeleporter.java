package com.seamlessmc.v1.core.cosmetics;

import com.seamlessmc.v1.core.APlayer;
import com.seamlessmc.v1.core.econ.ValuedObject;
import org.bukkit.Effect;
import org.bukkit.Location;

public class SeamlessTeleporter extends CosmeticEffect {
    {
        valuedObject = new ValuedObject("Seamless Teleporter", 1000.00, 1.1, 100, 0);
    }

    public String getName() {
        return super.name;
    }

    public Double getCost() {
        return 0.0;
    }

    public Integer getRequiredLevel() {
        return 0;
    }

    public String getString() {
        return null;
    }

    public void deployEffect(APlayer player) {
        Location loc = player.getPlayer().getPlayer().getLocation();
        int radius = 1;
        for (double y = 0; y <= 50; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            Location locc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
            loc.getWorld().playEffect(locc, Effect.SMOKE, 1);
        }
    }
}
