package eu.midnightdust.motschen.rocks.blockstates;

import net.minecraft.util.StringRepresentable;

public enum SeashellVariation implements StringRepresentable {
    YELLOW("yellow"),
    PINK("pink"),
    WHITE("white");

    private final String name;

    SeashellVariation(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }

    private static final SeashellVariation[] vals = values();

    public SeashellVariation next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
