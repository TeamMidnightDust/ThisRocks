package eu.midnightdust.motschen.rocks.util.geyser;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.block.custom.CustomBlockData;
import org.geysermc.geyser.api.block.custom.CustomBlockPermutation;
import org.geysermc.geyser.api.block.custom.NonVanillaCustomBlockData;
import org.geysermc.geyser.api.block.custom.component.BoxComponent;
import org.geysermc.geyser.api.block.custom.component.CustomBlockComponents;
import org.geysermc.geyser.api.block.custom.component.GeometryComponent;
import org.geysermc.geyser.api.block.custom.component.MaterialInstance;
import org.geysermc.geyser.api.block.custom.nonvanilla.JavaBlockState;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomBlocksEvent;
import org.geysermc.geyser.api.extension.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static eu.midnightdust.motschen.rocks.RocksMain.*;

public class GeyserBlocks implements Extension {
    static final BoxComponent SHAPE = new BoxComponent(-8, 0, -8, 8, 2, 8);
    static final BoxComponent SHAPE_LARGE = new BoxComponent(-8, 0, -8, 8, 3, 8);

    /*
    Bedrock follows a very different design than Java.
    A rock here will contain a property that defines its stone material type, rather than the variation.
    Each variation will be registered as one such rock block.
     */
    @Subscribe
    public void onDefineCustomBlocks(GeyserDefineCustomBlocksEvent event) {
        for (RockVariation variation : RockVariation.values()) {
            CustomBlockComponents components = CustomBlockComponents.builder()
                    .collisionBox(BoxComponent.emptyBox())
                    .selectionBox(variation == RockVariation.LARGE ? SHAPE_LARGE : SHAPE)
                    .geometry(GeometryComponent.builder()
                            .identifier(String.format("geometry.rocks.%s_rock", variation.asString()))
                            .build())
                    .lightEmission(0)
                    .lightDampening(0)
                    .friction(1f)
                    .build();

            CustomBlockData rockVariant = NonVanillaCustomBlockData.builder()
                    .name(variation.asString()+"_rock")
                    .namespace(MOD_ID)
                    .stringProperty("TYPE", Arrays.stream(RockType.values()).map(RockType::getName).collect(Collectors.toList()))
                    .components(components)
                    .permutations(createRockPermutations())
                    .includedInCreativeInventory(true)
                    .build();

            event.register(rockVariant);
            event.registerItemOverride(id(RockType.STONE.getName()).toString(), rockVariant);

            for (RockType type : RockType.values()) {
                String javaIdentifier = String.format("%s:%s[variation=%s]", MOD_ID, type.getName(), variation.asString());
                BlockState state = RocksMain.rocksByType.get(type).getDefaultState().with(ROCK_VARIATION, variation);

                JavaBlockState javaBlockState = JavaBlockState.builder()
                        .javaId(Block.getRawIdFromState(state))
                        .identifier(javaIdentifier)
                        .stateGroupId(Registries.BLOCK.getRawId(rocksByType.get(type)))
                        .waterlogged(state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED))
                        .canBreakWithHand(true)
                        .build();
                event.registerOverride(javaBlockState, rockVariant.blockStateBuilder()
                        .stringProperty("TYPE", type.getName())
                        .build());
            }
        }
    }

    private List<CustomBlockPermutation> createRockPermutations() {
        List<CustomBlockPermutation> permutations = new ArrayList<>();
        for (RockType type : RockType.values()) {
            MaterialInstance material = MaterialInstance.builder()
                    .texture(String.format("minecraft.%s", type.name().toLowerCase(Locale.ROOT)))
                    .renderMethod("opaque")
                    .ambientOcclusion(true)
                    .build();
            CustomBlockComponents components = CustomBlockComponents.builder()
                    .materialInstance("up", material)
                    .materialInstance("down", material)
                    .materialInstance("north", material)
                    .materialInstance("south", material)
                    .materialInstance("east", material)
                    .materialInstance("west", material)
                    .build();
            String condition = String.format("query.block_property('%s') == %s", "TYPE", type.getName());
            permutations.add(new CustomBlockPermutation(components, condition));
        }
        return permutations;
    }
}