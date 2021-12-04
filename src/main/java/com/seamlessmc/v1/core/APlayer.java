package com.seamlessmc.v1.core;

import com.seamlessmc.v1.core.cosmetics.Tag;
import com.seamlessmc.v1.core.econ.ValuedObject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import us.anvildevelopment.v1.util1.database.SQLConnector;
import us.anvildevelopment.v1.util1.database.annotations.MemoryOnly;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class APlayer {
    @MemoryOnly
    private static ConcurrentHashMap<String, APlayer> playerMap = new ConcurrentHashMap<>();
    @MemoryOnly
    private static SQLConnector userConnector = new SQLConnector(Configuration.sql_username, Configuration.sql_password, "CoreUsers", Configuration.sql_schema);
    @MemoryOnly
    private Player player;
    private List<String> vInv = new ArrayList<>();
    private String activeTag = "None";
    private int level = 0;
    private String uuid;

    public APlayer(String uuid) {
        this.uuid = uuid;
    }

    public static APlayer getPlayer(String uuid) throws IOException, IllegalAccessException, InstantiationException {
        if (playerMap.containsKey(uuid)) {
            return playerMap.get(uuid);
        } else {
            APlayer user = userConnector.getObject("uuid", uuid, APlayer.class);
            user.uuid = uuid;
            playerMap.put(uuid, user);
            return user;
        }
    }

    public Player getPlayer() {
        if (player == null) {
            player = Bukkit.getPlayer(uuid);
        }
        return player;
    }
    public String getPlaceholder() {
        if (activeTag.equalsIgnoreCase("None")) {
            return "";
        } else {
           return SpigotHook.getInstance().tagManager.tagMap.get(activeTag).getParsedPlaceholder(this);
        }
    }
    public void addObject(ValuedObject vo) {
        if (vo.getInCirc() < vo.getCap()) {
            vo.add();
            vInv.add(vo.objectID);
        }
    }
    public Tag getActiveTag() {return SpigotHook.getInstance().tagManager.tagMap.get(activeTag);}
    public void setActiveTag(String t) {activeTag = t;}
    //BattlePass
    public int getLevel() {return level;}
    public void setLevel(int i) {level = i;}
    public void addLevel(int i) {level = level + i;}
}
