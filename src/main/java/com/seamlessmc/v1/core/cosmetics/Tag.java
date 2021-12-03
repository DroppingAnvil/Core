package com.seamlessmc.v1.core.cosmetics;

import com.seamlessmc.v1.core.APlayer;
import com.seamlessmc.v1.core.Util;
import com.seamlessmc.v1.core.econ.ValuedObject;

public class Tag extends CosmeticEffect {
    public String id;
    private String tag;
    private String name;
    public Tag(String id, String tag, Double base, Double multiplier, Integer cap,String name) {
        valuedObject = new ValuedObject(name, base, multiplier, cap, 0);
        this.tag = tag;
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getParsedPlaceholder(APlayer a) {
        return Util.getInstance().parseString(tag, a);
    }
}
