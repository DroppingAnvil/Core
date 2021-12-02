package com.seamlessmc.v1.core.econ;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SeamlessEco implements Runnable {
    public String ecoID = "New Eco";
    public Map<String, ValuedObject> econObjects = new ConcurrentHashMap<String, ValuedObject>();

    public void run() {
        if (ecoID.equalsIgnoreCase("New Eco")) {

            ecoID = UUID.randomUUID().toString();
        }
    }
}
