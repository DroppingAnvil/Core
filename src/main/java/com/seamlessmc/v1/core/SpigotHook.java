package com.seamlessmc.v1.core;

import com.seamlessmc.v1.core.econ.Rarity;
import com.seamlessmc.v1.core.econ.ValuedObject;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class SpigotHook extends JavaPlugin {
    public static SpigotHook instance;
    public SpigotHook() {instance = this;}
    public static SpigotHook getInstance() {return instance;}
    public HashMap<OfflinePlayer, APlayer> aPlayers = new HashMap<OfflinePlayer, APlayer>();
    public HashMap<String, Tag> tags = new HashMap<String, Tag>();
    public Integer v = 13;
    public Tag def;
    public FileConfiguration data;
    public HashMap<String, ValuedObject> values = new HashMap<String, ValuedObject>();
    public HashMap<Rarity, String> cMap = new HashMap<Rarity, String>();

    public FileConfiguration getUserData(OfflinePlayer op) {
        File f = new File(getDataFolder(),op.getUniqueId() + ".yml");
        if (f.exists()) {
            return YamlConfiguration.loadConfiguration(f);
        } else {createUserData(op); return getUserData(op);}
    }

    public void createUserData(OfflinePlayer op) {
        File f = new File(getDataFolder(), op.getUniqueId() + ".yml");
        try {
            f.createNewFile();
        } catch (IOException e) {e.printStackTrace(); return;}
        FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
        List<String> mid = new ArrayList<String>();
        if (getConfig().getStringList("Defaults.Tags") != null) {fc.set("Tags", getConfig().getStringList("Defaults.Tags"));} else {fc.set("Tags", mid);}
        if (getConfig().getStringList("Defaults.PersistTags") != null) {fc.set("Persist.Tags", getConfig().getStringList("Defaults.PersistTags"));} else {fc.set("Persist.Tags", mid);}
        fc.set("ActiveTag", def.getName());
        try {
            fc.save(f);
        } catch (IOException e) {e.printStackTrace();}
    }

    @Deprecated
    public APlayer getFromUUID(UUID id) {
        for (APlayer a : aPlayers.values()) {
            if (a.getOfflinePlayer().getUniqueId().equals(id)) { return a; }
        }
        return null;
    }

    public APlayer getFromOfflinePlayer(OfflinePlayer op) {
        return aPlayers.get(op);
    }
    public Tag getFromName(String n) {
        if (tags.keySet().contains(n)) { return tags.get(n); }
        return def;
    }
    public ValuedObject lookupValue(String s) {
        if (values.keySet().contains(s)) {return values.get(s);}
        return null;
    }
    @Override
    public void onEnable() {
        //TODO new File(getDataFolder() + "/Users/").mkdir();
        this.saveResource("data.yml", false);
        this.saveResource("config.yml", false);
        data = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"data.yml"));
        this.reloadConfig();
        for (String key : getConfig().getConfigurationSection("Tags").getKeys(false)) {
            tags.put(key, new Tag(getConfig().getString("Tags." + key), key));
        }
        getLogger().log(Level.INFO, "Registered Tags. Total Tags registered: " + tags.size());
        for (String key : getConfig().getConfigurationSection("Economy").getKeys(false)) {
            values.put(key, new ValuedObject(key, getConfig().getDouble("Economy." + key + ".Base"), getConfig().getDouble("Economy." + key + ".Multiplier"), getConfig().getInt("Economy." + key + ".Cap"), data.getInt("ValueScale." + key)));
        }
        getLogger().log(Level.INFO, "Created dynamic economy objects. Total objects registered: " + values.size());
        for (String key : getConfig().getConfigurationSection("EconomySettings.RarityFormat").getKeys(false)) {
            cMap.put(Rarity.valueOf(key), getConfig().getString("EconomySettings.RarityFormat." + key));
        }
        if (cMap.size() != 5) {getLogger().log(Level.WARNING, "EconomySections in config.yml is configured incorrectly! Expected to find 5 rarities found " + cMap.size());} else {
            getLogger().log(Level.INFO, "Loaded Rarity formats. Total formats mapped: " + cMap.size());
        }
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        String packageName = getServer().getClass().getPackage().getName();
        try {
            v = Integer.valueOf(packageName.substring(packageName.lastIndexOf('.') + 1).split("_")[1]);
        } catch (NumberFormatException e) {
            getLogger().log(Level.WARNING, "Could not get your server's NMS version! Assuming 1.13 Bukkit API");
        }
        getLogger().log(Level.INFO, "Registering CoreAPI to Placeholder API");
        new PAPI().register();
        getLogger().log(Level.INFO, "Successfully registered CoreAPI to Placeholder API");
        if (getConfig().getString("Defaults.Tag") == null) {
            def = new Tag("&4CoreAPI configured incorrectly!", "Error");
            tags.put("Error", def);
        } else {def = getFromName(getConfig().getString("Defaults.Tag"));}
    }
    @Override
    public void onDisable() {
        for (APlayer a : aPlayers.values()) {
            a.save();
        }
        for (ValuedObject v : values.values()) {
            v.save();
        }
        try {
            data.save(new File(getDataFolder(), "data.yml"));
        } catch (IOException e) {e.printStackTrace();}
    }
}
