package io.github.droppinganvil;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APlayer {
    private OfflinePlayer player;
    private List<String> tags;
    private Tag active;
    private int level;
    private FileConfiguration conf;
    public APlayer(OfflinePlayer p, Boolean first) {
        player = p;
        conf = Core.getInstance().getUserData(p);
        if (first && conf.getStringList("Tags") != null) {
            for (String s : conf.getStringList("Tags")) {
                Util.getInstance().sendTagReceived(this, Core.getInstance().getFromName(s));
            }
        }
        tags = conf.getStringList("Tags");
        if (tags == null) {tags = new ArrayList<String>();}
        active = Core.getInstance().getFromName(conf.getString("ActiveTag"));
        level = conf.getInt("BattlePass.Level", 0);
    }
    public OfflinePlayer getOfflinePlayer() {
        return player;
    }
    //Tags
    public List<Tag> getTags() {
        List<Tag> tagsL = new ArrayList<Tag>();
        for (String s : tags) {
            tagsL.add(Core.getInstance().getFromName(s));
        }
        return tagsL;
    }
    public String getPlaceholder() {
        if (active == null) {active = Core.getInstance().def;}
        return active.getParsedPlaceholder(this);
    }
    public void addTag(Tag t) {
        tags.add(t.getName());
    }
    public void addPersistTag(Tag t) {
        List<String> ptags = conf.getStringList("Persist.Tags");
        ptags.add(t.getName());
        conf.set("Persist.Tags", ptags);
    }
    public void removeTag(Tag t) {
        if (tags.contains(t.getName())) {
            tags.remove(t.getName());
        }
    }
    public Tag getActiveTag() {return active;}
    public void setActiveTag(Tag t) {active = t;}
    //BattlePass
    public int getLevel() {return level;}
    public void setLevel(int i) {level = i;}
    public void addLevel(int i) {level = level + i;}
    //Storage
    public void seasonCleanup() {
        tags = conf.getStringList("Persist.Tags");
        active = Core.getInstance().def;
        level = 0;
    }
    public void save() {
        conf.set("Tags", tags);
        conf.set("ActiveTag", active.getName());
        conf.set("BattlePass.Level", level);
        try {
            conf.save(new File(Core.getInstance().getDataFolder(),player.getUniqueId() + ".yml"));
        } catch (IOException e) {e.printStackTrace();}
    }
}
