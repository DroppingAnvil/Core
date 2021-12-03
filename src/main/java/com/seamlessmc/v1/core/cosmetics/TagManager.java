package com.seamlessmc.v1.core.cosmetics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TagManager {
    public String id = "01";
    public Map<String, Tag> tagMap = new ConcurrentHashMap<>();
}
