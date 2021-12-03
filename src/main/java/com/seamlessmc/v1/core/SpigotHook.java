package com.seamlessmc.v1.core;

import com.seamlessmc.v1.core.cosmetics.CosmeticEffect;
import com.seamlessmc.v1.core.cosmetics.CosmeticManager;
import com.seamlessmc.v1.core.cosmetics.TagManager;
import com.seamlessmc.v1.core.econ.SeamlessEco;
import org.bukkit.plugin.java.JavaPlugin;
import us.anvildevelopment.v1.util1.database.SQLConnector;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

public class SpigotHook extends JavaPlugin {
    public static SpigotHook instance;
    public SpigotHook() {instance = this;}
    public static SpigotHook getInstance() {return instance;}
    public SQLConnector effectsConnector;
    public SQLConnector tagsConnector;
    public CosmeticManager cosmeticManager = new CosmeticManager();
    public TagManager tagManager = new TagManager();
    public HashMap<String, APlayer> aPlayers = new HashMap<>();
    public Integer v = 20;
    public SeamlessEco primaryEco = new SeamlessEco();
    @Override
    public void onEnable() {
        this.reloadConfig();
        effectsConnector = new SQLConnector(getConfig().getString("Database.Username"), getConfig().getString("Database.Password"), "Effects", getConfig().getString("Database.ConnectionAddress") ,getConfig().getString("Database.Schema"));
        tagsConnector = new SQLConnector(getConfig().getString("Database.Username"), getConfig().getString("Database.Password"), "Tags", getConfig().getString("Database.ConnectionAddress") ,getConfig().getString("Database.Schema"));
        try {
            cosmeticManager = effectsConnector.getObject("id", "01", CosmeticManager.class);
        } catch (IllegalAccessException | InstantiationException | IOException e) {
            e.printStackTrace();
        }
        for (CosmeticEffect ce : cosmeticManager.effectMap.values()) {
            primaryEco.econObjects.put(ce.valuedObject.objectID, ce.valuedObject);
        }
        try {
            tagManager = tagsConnector.getObject("id", "01", TagManager.class);
        } catch (IllegalAccessException | InstantiationException | IOException e) {
            e.printStackTrace();
        }
        for (CosmeticEffect ce : tagManager.tagMap.values()) {
            primaryEco.econObjects.put(ce.valuedObject.objectID, ce.valuedObject);
        }
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        String packageName = getServer().getClass().getPackage().getName();
        try {
            v = Integer.valueOf(packageName.substring(packageName.lastIndexOf('.') + 1).split("_")[1]);
        } catch (NumberFormatException e) {
            getLogger().log(Level.WARNING, "Could not get your server's NMS version! Assuming 1.17 Spigot API");
        }
        getLogger().log(Level.INFO, "Registering CoreAPI to Placeholder API");
        new PAPI().register();
        getLogger().log(Level.INFO, "Successfully registered CoreAPI to Placeholder API");
    }
    @Override
    public void onDisable() {

    }
}
