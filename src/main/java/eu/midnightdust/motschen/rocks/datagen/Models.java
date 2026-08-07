package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import com.mojang.math.Quadrant;

import java.util.*;

public class Models extends FabricModelProvider {
    public static final TextureSlot ZERO_TEXTURE_KEY = TextureSlot.of("0");
    public Models(FabricDataOutput output) {
        super(output);
    }

    public static Identifier getBlockId(String s) {
        return RocksMain.id("block/"+s);
    }
    public static Identifier getItemId(String s) {
        return RocksMain.id("item/"+s);
    }
    public static ModelTemplate getSimpleParentModel(Identifier parentId, String variant) {
        return new ModelTemplate(Optional.of(parentId), Optional.of(variant), ZERO_TEXTURE_KEY);
    }
    @Override
    public void generateBlockStateModels(BlockModelGenerators bsModelGenerator) {
        for (RockType type : RockType.values()) {
            Block block = BuiltInRegistries.BLOCK.get(RocksMain.id(type.getName()));
            RockModel.registerBlockModel(bsModelGenerator, block, type.getStoneBlock());
        }
        for (StickType type : StickType.values()) {
            Block block = BuiltInRegistries.BLOCK.get(RocksMain.id(type.getName()+"_stick"));
            StickModel.registerBlockModel(bsModelGenerator, block, type.getBaseBlock());
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        for (RockType type : RockType.values()) {
            Item item = BuiltInRegistries.ITEM.get(RocksMain.id(type.getName()));
            registerParentedItemModel(itemModelGenerator, item, getItemId("rock_base"), type.getStoneBlock());

            if (type != RockType.GRAVEL) {
                Item splitter = BuiltInRegistries.ITEM.get(RocksMain.id(type.getFragment().getName()));
                registerParentedItemModel(itemModelGenerator, splitter, getItemId("splitter_base"), type.getFragment().getStoneBlock());
            }
        }
        for (StickType type : StickType.values()) {
            Item item = BuiltInRegistries.ITEM.get(RocksMain.id(type.getName()+"_stick"));
            registerParentedItemModel(itemModelGenerator, item, getItemId("stick_base"), type.getBaseBlock());
        }
        itemModelGenerator.register(RocksMain.Geyser.asItem());
        itemModelGenerator.register(RocksMain.NetherGeyser.asItem());
        registerStarfishItemVariations(itemModelGenerator, RocksMain.Starfish);
        itemModelGenerator.register(RocksMain.Seashell.asItem());
        itemModelGenerator.register(RocksMain.Pinecone.asItem());
    }
    public static void registerParentedItemModel(ItemModelGenerators modelGenerator, Item item, Identifier parentId, Block textureSource) {
        TextureMapping textureMap = TextureMapping.of(ZERO_TEXTURE_KEY, TextureMapping.getId(textureSource));

        Identifier itemModel = getSimpleParentModel(parentId, "").upload(item, textureMap, modelGenerator.modelCollector);
        modelGenerator.output.accept(item, ItemModelUtils.basic(itemModel));
    }
    public final void registerStarfishItemVariations(ItemModelGenerators modelGenerator, Block starfish) {
        Map<StarfishVariation, ItemModel.Unbaked> variantMap = new HashMap<>();
        for (StarfishVariation variation : StarfishVariation.values()) {
            variantMap.put(variation, ItemModelUtils.basic(ModelLocationUtils.getItemSubModelId(starfish.asItem(), "_"+variation.toString())));
        }
        modelGenerator.output.accept(starfish.asItem(), ItemModelUtils.select(RocksMain.STARFISH_VARIATION, ItemModelUtils.basic(ModelLocationUtils.getItemModelId(starfish.asItem())), variantMap));
    }

    public static MultiVariant getRandomRotationWeightedVariant(Identifier modelId) {
        WeightedList.Builder<Variant> list = WeightedList.builder();
        for (Quadrant rotation : Quadrant.values()) {
            Variant rotatedVariant = new Variant(modelId, Variant.SimpleModelState.DEFAULT.setRotationY(rotation));
            list.add(rotatedVariant);
        }
        return new MultiVariant(list.build());
    }

    private static class RockModel {
        public static void registerBlockModel(BlockModelGenerators modelGenerator, Block rockBlock, Block textureSource) {
            TextureMapping textureMap = TextureMapping.of(ZERO_TEXTURE_KEY, TextureMapping.getId(textureSource));

            Identifier largeRock = getSimpleParentModel(getBlockId("large_rock"), "_large").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_rock"), "_medium").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            Identifier smallRock = getSimpleParentModel(getBlockId("small_rock"), "_small").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            Identifier tinyRock = getSimpleParentModel(getBlockId("tiny_rock"), "_tiny").upload(rockBlock, textureMap, modelGenerator.modelCollector);
            modelGenerator.blockStateCollector.accept(createBlockState(rockBlock, new Identifier[]{largeRock, mediumRock, smallRock, tinyRock}));
        }

        private static BlockModelDefinitionGenerator createBlockState(Block rockBlock, Identifier[] modelIds) {
            return MultiVariantGenerator.of(rockBlock)
                    .with(PropertyDispatch.models(RocksMain.ROCK_VARIATION)
                            .generate(variation -> getRandomRotationWeightedVariant(modelIds[3 - variation.ordinal()]))
                    );
        }
    }
    private static class StickModel {
        public static void registerBlockModel(BlockModelGenerators modelGenerator, Block stickBlock, Block textureSource) {
            TextureMapping textureMap = TextureMapping.of(ZERO_TEXTURE_KEY, TextureMapping.getId(textureSource));

            Identifier largeRock = getSimpleParentModel(getBlockId("large_stick"), "_large").upload(stickBlock, textureMap, modelGenerator.modelCollector);
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_stick"), "_medium").upload(stickBlock, textureMap, modelGenerator.modelCollector);
            Identifier smallRock = getSimpleParentModel(getBlockId("small_stick"), "_small").upload(stickBlock, textureMap, modelGenerator.modelCollector);
            modelGenerator.blockStateCollector.accept(createBlockState(stickBlock, new Identifier[]{largeRock, mediumRock, smallRock}));
        }

        private static BlockModelDefinitionGenerator createBlockState(Block stickBlock, Identifier[] modelIds) {
            return MultiVariantGenerator.of(stickBlock)
                    .with(PropertyDispatch.models(RocksMain.STICK_VARIATION)
                            .generate(variation -> getRandomRotationWeightedVariant(modelIds[2 - variation.ordinal()]))
                    );
        }
    }
}
