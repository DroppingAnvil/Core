package com.seamlessmc.v1.core;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PAPI extends PlaceholderExpansion {
    public PAPI() {}
    @Override
    public boolean persist(){
        return true;
    }
    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public String getAuthor(){
        return "DroppingAnvil";
    }
    @Override
    public String getIdentifier(){
        return "Core";
    }
    @Override
    public String getVersion(){
        return SpigotHook.getInstance().getDescription().getVersion();
    }
    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        String invalid = ChatColor.RED + "Invalid AnvilAPI call";
        if(player == null){
            return "";
        }
        if(identifier.equals("tag")) {
            return SpigotHook.getInstance().getFromOfflinePlayer(player).getPlaceholder();
        }
        if(identifier.equals("battlepasslevel")) {
            return String.valueOf(SpigotHook.getInstance().getFromOfflinePlayer(player).getLevel());
        }
        if(identifier.contains(".Cost")) {
            ValuedObject v = SpigotHook.getInstance().lookupValue(identifier.replace(".Cost", ""));
            if (v != null) {return ChatColor.GREEN + " $" + v.getValue() + ChatColor.RESET + " ";}
        }
        if(identifier.contains(".RarityExact")) {
            ValuedObject v = SpigotHook.getInstance().lookupValue(identifier.replace(".RarityExact", ""));
            if (v != null) {return v.getRarityExactColored();} else {return invalid;}
        }
        if(identifier.contains(".Rarity")) {
            ValuedObject v = SpigotHook.getInstance().lookupValue(identifier.replace(".Rarity", ""));
            if (v != null) {return v.getRarityColored();}
        }
        if(identifier.contains(".InCirculation")) {
            ValuedObject v = SpigotHook.getInstance().lookupValue(identifier.replace(".InCirculation", ""));
            if (v != null) {return v.getInCircColored();}
        }
        if(identifier.contains(".Info")) {
            ValuedObject v = SpigotHook.getInstance().lookupValue(identifier.replace(".Info", ""));
            if (v != null) {
                return ChatColor.AQUA + "Official current value data for ValuedObject: " + ChatColor.BOLD + v.getName() + " " + ChatColor.RESET + ChatColor.GRAY + "Current estimated value: " + ChatColor.GREEN + " $" + v.getValue() + ChatColor.GRAY + " Current Rarity: " + v.getRarityColored() + ChatColor.GRAY + " Currently owned: " + v.getInCircColored();
            }
        }
        if(identifier.contains(".Recalc")) {
            ValuedObject v = SpigotHook.getInstance().lookupValue(identifier.replace(".Recalc", ""));
            v.recalc();
            return ChatColor.AQUA + "Recalc called, using your UUID as authorization.";
        }
        if(identifier.equals("Reload")) {
            SpigotHook.getInstance().reloadConfig();
        }
        return invalid;
    }
}
