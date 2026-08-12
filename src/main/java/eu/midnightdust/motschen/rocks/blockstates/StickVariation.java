package eu.midnightdust.motschen.rocks.blockstates;

import net.minecraft.util.StringRepresentable;

public enum StickVariation implements StringRepresentable {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String name;

    StickVariation(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }

    private static final StickVariation[] vals = values();

    public StickVariation next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
