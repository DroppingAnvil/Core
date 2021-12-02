package com.seamlessmc.v1.core.cosmetics;

import com.seamlessmc.v1.core.APlayer;
import com.seamlessmc.v1.core.econ.ValuedObject;

public interface CosmeticEffect {
    ValuedObject getValue();
    String getName();
    Double getCost();
    Integer getRequiredLevel();
    /**
     * Should be used by the system to force effects
     * @param player Target
     */
    void deployEffect(APlayer player);
}
