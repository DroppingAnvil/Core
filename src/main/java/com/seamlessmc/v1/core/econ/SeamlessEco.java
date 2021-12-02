package com.seamlessmc.v1.core.econ;

import com.seamlessmc.v1.core.SpigotHook;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class SeamlessEco implements Runnable {
    public String ecoID = "New Eco";
    public Integer consumers = 0;
    public Map<String, ValuedObject> econObjects = new ConcurrentHashMap<String, ValuedObject>();

    public void run() {
        for (ValuedObject vo : econObjects.values()) {
            vo.recalc(this);
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void preAsync() {
        if (ecoID.equalsIgnoreCase("New Eco")) {
            SpigotHook.instance.getLogger().log(Level.WARNING, "SeamlessEco could not locate an existing Economy!");
            ecoID = UUID.randomUUID().toString();
        } else if (ecoID.contains("!")) {
            SpigotHook.instance.getLogger().log(Level.INFO, "Modified Economy '"+ecoID+"'" + " has been registered");
        }
    }
}
