package com.seamlessmc.v1.core;

import com.seamlessmc.v1.core.econ.ValuedObject;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Util {
    static Util instance = null;
    private Util() {}
    static public Util getInstance()
    {
        if (instance == null)
            instance = new Util();
        return instance;
    }
    public String parseString(String s, APlayer a) {
        String ss = ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(a.getOfflinePlayer(), s));
        if (ss.contains("{")) {
            if (ss.contains("{BattlePassLevel}")) {ss = ss.replace("{BattlePassTier}", String.valueOf(a.getLevel()));}
        }
        return ss;
    }
    public List<String> parseStringList(List<String> sl, APlayer a) {
        List<String> sll = new ArrayList<String>();
        for (String s : sl) {
            String ss = ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(a.getOfflinePlayer(), s));
            if (ss.contains("{")) {
                if (ss.contains("{BattlePassLevel}")) {
                    ss = ss.replace("{BattlePassLevel}", String.valueOf(a.getLevel()));
                }
            }
            sll.add(ss);
        }
        return sll;
    }
    @Deprecated
    public void awardTag(APlayer a, Tag t) {
        a.addTag(t);
    }
    public void awardTag(APlayer a, Tag tag, Boolean persist) {
        ValuedObject ta = SpigotHook.getInstance().lookupValue(tag.getName());
        if (ta != null) {
            if (ta.getInCirc() >= ta.getCap()) {
                SpigotHook.getInstance().getLogger().log(Level.INFO, "Tag give rejected as the ValuedObject is capped!");return;}
            ta.add();
        }
        if (persist) {a.addPersistTag(tag); sendTagReceived(a, tag);} else {a.addTag(tag); sendTagReceived(a, tag);}
    }
    public void sendOnlyPlayers(CommandSender s) {
        s.sendMessage(SpigotHook.getInstance().getConfig().getString("Messages.PlayersOnly", "You must be a player to do this!"));
    }
    public void sendTagAdminNoPerms(APlayer a) {
        a.getOfflinePlayer().getPlayer().sendMessage(parseString(SpigotHook.getInstance().getConfig().getString("Messages.TagAdminNoPerms", "&bTry /tag"), a));
    }
    public void sendTagTransfer(APlayer a, APlayer b, Tag t) {
    }
    @Deprecated
    public void sendTagReceived(APlayer a, Tag t) {
        Player p = a.getOfflinePlayer().getPlayer();
        p.sendMessage(parseString(SpigotHook.getInstance().getConfig().getString("Messages.TagReceived").replace("{TAGNAME}", t.getName()), a));
        p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
    }
}
