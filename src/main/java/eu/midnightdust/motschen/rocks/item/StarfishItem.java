package eu.midnightdust.motschen.rocks.item;

import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import net.minecraft.item.Item;

public class StarfishItem extends Item {
    public final StarfishVariation variation;

    public StarfishItem(Settings settings, StarfishVariation variation) {
        super(settings);
        this.variation = variation;
    }
}
