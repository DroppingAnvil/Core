package io.github.droppinganvil;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ValuedObject {
    private String name;
    private double multiplier;
    private double base;
    private int cap;
    private int incirc;
    private Rarity r;
    private int pInCirc;
    private ChangeTypes state;
    private double current;
    private String chatcar;
    public ValuedObject(String name, Double base, Double multiplier, Integer cap, Integer incirc) {
        this.name = name;
        this.base = base;
        this.multiplier = multiplier;
        this.cap = cap;
        this.incirc = incirc;
        System.out.print(incirc);
        recalc();
    }
    public void recalc() {
        calcRarity();
        switch (r) {
            case Common:
                calcStateAndSet(base); break;
            case Uncommon:
                calcStateAndSet(base * multiplier); break;
            case Rare:
                calcStateAndSet(base * (multiplier * 2)); break;
            case Epic:
                calcStateAndSet(base * (multiplier * 3)); break;
            case Legendary:
                calcStateAndSet(base * (multiplier * 4)); break;
        }
    }
    public void calcStateAndSet(Double d) {
        if (d > current) {state = ChangeTypes.Up; setCurrent(d); return;}
        if (d == current) {state = ChangeTypes.Neutral; setCurrent(d); return;}
        if (d < current) {state = ChangeTypes.Down; setCurrent(d); return;}
    }
    public void setCurrent(Double d) {current = d;}
    public void calcRarity() {
            double p = ((((double) incirc) / ((double) cap)) * 100);
            pInCirc = (int) Math.round(p);
        if (pInCirc < 20) {r = Rarity.Common; return;}
        if (pInCirc < 40 && pInCirc >= 20) {r = Rarity.Uncommon; return;}
        if (pInCirc < 60 && pInCirc >= 40) {r = Rarity.Rare; return;}
        if (pInCirc < 80 && pInCirc >= 60) {r = Rarity.Epic; return;}
        if (pInCirc >= 80) {r = Rarity.Legendary; return;}
    }
    public double getValue() {return current;}
    public Rarity getRarity() {return r;}
    public String getRarityColored() {
        return Core.getInstance().cMap.get(r) + r.name() + ChatColor.RESET;
    }
    public String getRarityExactColored() {
        return Core.getInstance().cMap.get(r) + pInCirc + ChatColor.RESET;
    }
    public String getInCircColored() {
        return Core.getInstance().cMap.get(r) + incirc + ChatColor.RESET;
    }
    public String getCapColored() {return ChatColor.AQUA + String.valueOf(cap) + ChatColor.RESET;}
    public Integer getInCirc() {return this.incirc;}
    public Integer getCap() {return this.cap;}
    public Integer getExactRarity() { return pInCirc; }
    public ChangeTypes getState() {return state;}
    public void add() {incirc++;}
    public void remove() {incirc--;}
    public String getName() {return name;}
    public void save() {
        Core.getInstance().data.set(name, incirc);
    }
}
