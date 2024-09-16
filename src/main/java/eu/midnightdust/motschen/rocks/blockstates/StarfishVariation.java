package eu.midnightdust.motschen.rocks.blockstates;

import net.minecraft.util.StringIdentifiable;

public enum StarfishVariation implements StringIdentifiable {
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

    public String asString() {
        return this.name;
    }

    private static final StarfishVariation[] vals = values();

    public StarfishVariation next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
