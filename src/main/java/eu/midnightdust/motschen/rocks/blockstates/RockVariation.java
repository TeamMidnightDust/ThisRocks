package eu.midnightdust.motschen.rocks.blockstates;

import net.minecraft.util.StringRepresentable;

public enum RockVariation implements StringRepresentable {
    TINY("tiny"),
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String name;

    RockVariation(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }

    private static final RockVariation[] vals = values();

    public RockVariation next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
