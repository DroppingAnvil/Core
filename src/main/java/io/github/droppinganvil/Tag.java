package io.github.droppinganvil;

import io.github.droppinganvil.APlayer;

public class Tag {
    private String ta;
    private String n;
    public Tag(String tag, String name) {
        ta = tag;
        n = name;
    }
    public String getName() {
        return n;
    }
    public String getParsedPlaceholder(APlayer a) {
        return Util.getInstance().parseString(ta, a);
    }
}
