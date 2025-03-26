package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;

public class Models extends FabricModelProvider {
    public static final TextureKey ZERO_TEXTURE_KEY = TextureKey.of("0");
    public Models(FabricDataOutput output) {
        super(output);
    }

    public static Identifier getBlockId(String s) {
        return RocksMain.id("block/"+s);
    }
    public static Identifier getItemId(String s) {
        return RocksMain.id("item/"+s);
    }
    public static Model getSimpleParentModel(Identifier parentId, String variant) {
        return new Model(Optional.of(parentId), Optional.of(variant), ZERO_TEXTURE_KEY);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator bsModelGenerator) {
        for (RockType type : RockType.values()) {
            Block block = Registries.BLOCK.get(RocksMain.id(type.getName()));
            RockModel.registerBlockModel(bsModelGenerator, block, type.getStoneBlock());
        }
        for (StickType type : StickType.values()) {
            Block block = Registries.BLOCK.get(RocksMain.id(type.getName()+"_stick"));
            StickModel.registerBlockModel(bsModelGenerator, block, type.getBaseBlock());
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (RockType type : RockType.values()) {
            Item item = Registries.ITEM.get(RocksMain.id(type.getName()));
            registerParentedItemModel(itemModelGenerator, item, getItemId("rock_base"), type.getStoneBlock());

            if (type != RockType.GRAVEL) {
                Item splitter = Registries.ITEM.get(RocksMain.id(type.getSplitterName()));
                registerParentedItemModel(itemModelGenerator, splitter, getItemId("splitter_base"), type.getStoneBlock());
            }
        }
        for (StickType type : StickType.values()) {
            Item item = Registries.ITEM.get(RocksMain.id(type.getName()+"_stick"));
            registerParentedItemModel(itemModelGenerator, item, getItemId("stick_base"), type.getBaseBlock());
        }
        itemModelGenerator.register(RocksMain.Geyser.asItem());
        itemModelGenerator.register(RocksMain.NetherGeyser.asItem());
        registerStarfishItemVariations(itemModelGenerator, RocksMain.Starfish);
        itemModelGenerator.register(RocksMain.Seashell.asItem());
        itemModelGenerator.register(RocksMain.Pinecone.asItem());
    }
    public static void registerParentedItemModel(ItemModelGenerator modelGenerator, Item item, Identifier parentId, Block textureSource) {
        TextureMap textureMap = TextureMap.of(ZERO_TEXTURE_KEY, TextureMap.getId(textureSource));

        Identifier itemModel = getSimpleParentModel(parentId, "").upload(item, textureMap, modelGenerator.modelCollector);
        modelGenerator.output.accept(item, ItemModels.basic(itemModel));
    }
    public final void registerStarfishItemVariations(ItemModelGenerator modelGenerator, Block starfish) {
        Map<StarfishVariation, ItemModel.Unbaked> variantMap = new HashMap<>();
        for (StarfishVariation variation : StarfishVariation.values()) {
            variantMap.put(variation, ItemModels.basic(ModelIds.getBlockSubModelId(starfish, "_"+variation.toString())));
        }
        modelGenerator.output.accept(starfish.asItem(), ItemModels.select(RocksMain.STARFISH_VARIATION, ItemModels.basic(ModelIds.getItemModelId(starfish.asItem())), variantMap));
    }

    public static <T> List<BlockStateVariant> getRandomRotationVariants(VariantSetting<T> baseSettings, T value) {
        List<BlockStateVariant> list = new ArrayList<>();
        for (VariantSettings.Rotation rotation : VariantSettings.Rotation.values()) {
            BlockStateVariant rotatedVariant = BlockStateVariant.create().put(baseSettings, value);
            list.add(rotatedVariant.put(VariantSettings.Y, rotation));
        }
        return list;
    }

    private static class RockModel {
        public static void registerBlockModel(BlockStateModelGenerator modelGenerator, Block rockBlock, Block textureSource) {
            TextureMap textureMap = TextureMap.of(ZERO_TEXTURE_KEY, TextureMap.getId(textureSource));

            Identifier largeRock = getSimpleParentModel(getBlockId("large_rock"), "_large").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_rock"), "_medium").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            Identifier smallRock = getSimpleParentModel(getBlockId("small_rock"), "_small").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            Identifier tinyRock = getSimpleParentModel(getBlockId("tiny_rock"), "_tiny").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            modelGenerator.blockStateCollector.accept(createBlockState(rockBlock, new Identifier[]{largeRock, mediumRock, smallRock, tinyRock}));
        }

        private static BlockStateSupplier createBlockState(Block rockBlock, Identifier[] modelIds) {
            return VariantsBlockStateSupplier.create(rockBlock)
                    .coordinate(BlockStateVariantMap.create(RocksMain.ROCK_VARIATION)
                            .registerVariants(variation -> getRandomRotationVariants(VariantSettings.MODEL, modelIds[3 - variation.ordinal()]))
                    );
        }
    }
    private static class StickModel {
        public static void registerBlockModel(BlockStateModelGenerator modelGenerator, Block stickBlock, Block textureSource) {
            TextureMap textureMap = TextureMap.of(ZERO_TEXTURE_KEY, TextureMap.getId(textureSource));

            Identifier largeRock = getSimpleParentModel(getBlockId("large_stick"), "_large").upload(stickBlock, textureMap, modelGenerator.modelCollector);
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_stick"), "_medium").upload(stickBlock, textureMap, modelGenerator.modelCollector);
            Identifier smallRock = getSimpleParentModel(getBlockId("small_stick"), "_small").upload(stickBlock, textureMap, modelGenerator.modelCollector);
            modelGenerator.blockStateCollector.accept(createBlockState(stickBlock, new Identifier[]{largeRock, mediumRock, smallRock}));
        }

        private static BlockStateSupplier createBlockState(Block stickBlock, Identifier[] modelIds) {
            return VariantsBlockStateSupplier.create(stickBlock)
                    .coordinate(BlockStateVariantMap.create(RocksMain.STICK_VARIATION)
                            .registerVariants(variation -> getRandomRotationVariants(VariantSettings.MODEL, modelIds[2 - variation.ordinal()]))
                    );
        }
    }
}
