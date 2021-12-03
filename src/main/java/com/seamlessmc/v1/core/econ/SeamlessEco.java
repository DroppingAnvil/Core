package com.seamlessmc.v1.core.econ;

import us.anvildevelopment.v1.util1.database.annotations.MemoryOnly;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SeamlessEco implements Runnable {
    @MemoryOnly
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
}
