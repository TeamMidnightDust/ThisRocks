package eu.midnightdust.motschen.rocks;

import eu.midnightdust.lib.util.PlatformFunctions;
import eu.midnightdust.motschen.rocks.block.*;
import eu.midnightdust.motschen.rocks.block.blockentity.BlockEntityInit;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.networking.HelloPayload;
import eu.midnightdust.motschen.rocks.util.RockType;
import eu.midnightdust.motschen.rocks.util.StickType;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import eu.midnightdust.motschen.rocks.world.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.midnightdust.motschen.rocks.util.RegistryUtil.registerBlockWithItem;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.registerItem;
import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.*;

public class RocksMain implements ModInitializer {
    public static final String MOD_ID = "rocks";
    public static boolean polymerMode = hasRequiredPolymerModules();
    public static List<ServerPlayerEntity> playersWithMod = new ArrayList<>();

    public static final EnumProperty<RockVariation> ROCK_VARIATION = EnumProperty.of("variation", RockVariation.class);
    public static final EnumProperty<StickVariation> STICK_VARIATION = EnumProperty.of("variation", StickVariation.class);
    public static final EnumProperty<SeashellVariation> SEASHELL_VARIATION = EnumProperty.of("variation", SeashellVariation.class);
    public static final EnumProperty<StarfishVariation> STARFISH_VARIATION = EnumProperty.of("variation", StarfishVariation.class);

    public static Map<RockType, Rock> rocksByType = new HashMap<>();
    public static Map<StickType, Stick> sticksByType = new HashMap<>();
    public static Map<RockType, Item> splittersByType = new HashMap<>();

    public static final Identifier PINECONE = id("pinecone");
    public static final Identifier SEASHELL = id("seashell");
    public static final Identifier STARFISH = id("starfish");
    public static final Identifier GEYSER = id("geyser");
    public static final Identifier NETHER_GEYSER = id("nether_geyser");

    public static Block Pinecone;
    public static Block Seashell;
    public static Block Starfish;
    public static Block Geyser;
    public static Block NetherGeyser;

    public static List<ItemStack> groupItems = new ArrayList<>();
    public static ItemGroup RocksGroup;
    public static final RegistryKey<ItemGroup> ROCKS_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "rocks"));

    @Override
    public void onInitialize() {
        RocksConfig.init(MOD_ID, RocksConfig.class);
        if (polymerMode) polymerMode = RocksConfig.enablePolymerMode && (RocksConfig.forcePolymerMode || !PlatformFunctions.isClientEnv());

        PayloadTypeRegistry.playC2S().register(HelloPayload.PACKET_ID, HelloPayload.codec);
        ServerPlayNetworking.registerGlobalReceiver(HelloPayload.PACKET_ID, (payload, context) -> {
            if (!RocksConfig.forcePolymerMode) {
                playersWithMod.add(context.player());
                if (polymerMode) PolyUtil.hideElementHolders(context.player());
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((playNetworkHandler, server) -> {
            playersWithMod.remove(playNetworkHandler.player);
        });

        if (polymerMode) PolyUtil.init();

        for (RockType type : RockType.values()) {
            Identifier id = id(type.getName());
            rocksByType.put(type, registerBlockWithItem(id, polymerMode ? newRockPolymer(id) : new Rock(id)));

            if (type != RockType.GRAVEL)
                splittersByType.put(type, registerItem(id(type.getSplitterName()), simpleItem(id(type.getSplitterName()))));
        }
        for (StickType type : StickType.values()) {
            Identifier id = id(type.getName()+"_stick");
            sticksByType.put(type, registerBlockWithItem(id, polymerMode ? newStickPolymer(id) : new Stick(id)));
        }


        Pinecone = registerBlockWithItem(PINECONE, polymerMode ? newPineconePolymer(PINECONE) : new Pinecone(PINECONE));
        Seashell = registerBlockWithItem(SEASHELL, polymerMode ? newSeashellPolymer(SEASHELL) : new Seashell(SEASHELL));
        Starfish = registerBlockWithItem(STARFISH, polymerMode ? newStarfishPolymer(STARFISH) : new Starfish(STARFISH));

        Geyser = registerBlockWithItem(GEYSER, polymerMode ? newOverworldGeyserPolymer(GEYSER) : new OverworldGeyser(GEYSER));
        NetherGeyser = registerBlockWithItem(NETHER_GEYSER, polymerMode ? newNetherGeyserPolymer(NETHER_GEYSER) : new NetherGeyser(NETHER_GEYSER));

        registerItemGroup();

        FeatureRegistry.init();
        FeatureInjector.init();
        BlockEntityInit.init();
    }
    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    private static boolean hasRequiredPolymerModules() {
        return PlatformFunctions.isModLoaded("polymer-core") &&
                PlatformFunctions.isModLoaded("polymer-blocks") &&
                PlatformFunctions.isModLoaded("polymer-resource-pack") &&
                PlatformFunctions.isModLoaded("polymer-virtual-entity") &&
                PlatformFunctions.isModLoaded("factorytools");
    }

    public static Item simpleItem(Identifier id) {
        if (polymerMode) return PolyUtil.simplePolymerItem(id);
        return new Item(new Item.Settings());
    }

    public static void registerItemGroup() {
        if (polymerMode) PolyUtil.registerPolymerGroup();
        else {
            RocksGroup = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.rocks.rocks")).icon(() -> new ItemStack(rocksByType.get(RockType.STONE))).entries(((displayContext, entries) -> entries.addAll(groupItems))).build();
            Registry.register(Registries.ITEM_GROUP, ROCKS_GROUP, RocksGroup);
        }
    }
}
