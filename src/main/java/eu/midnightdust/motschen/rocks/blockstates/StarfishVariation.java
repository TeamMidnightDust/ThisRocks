package eu.midnightdust.motschen.rocks.blockstates;

import net.minecraft.util.StringRepresentable;

public enum StarfishVariation implements StringRepresentable {
    RED("red"),
    PINK("pink"),
    ORANGE("orange");

    public final String name;

    StarfishVariation(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }

    private static final StarfishVariation[] vals = values();

    public StarfishVariation next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
