package eu.midnightdust.motschen.rocks.datagen;

//~ if >= 26.1 'FabricDataOutput' -> 'FabricPackOutput' {
import com.mojang.math.Quadrant;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
//~ if >= 26.1 'net.minecraft.client.renderer.block.model.Variant' -> 'net.minecraft.client.renderer.block.dispatch.Variant'
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.*;

public class Models extends FabricModelProvider {
    public static final TextureSlot ZERO_TEXTURE_KEY = TextureSlot.create("0");
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
            Block block = BuiltInRegistries.BLOCK.getValue(RocksMain.id(type.getName()));
            RockModel.registerBlockModel(bsModelGenerator, block, type.getStoneBlock());
        }
        for (StickType type : StickType.values()) {
            Block block = BuiltInRegistries.BLOCK.getValue(RocksMain.id(type.getName()+"_stick"));
            StickModel.registerBlockModel(bsModelGenerator, block, type.getBaseBlock());
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        for (RockType type : RockType.values()) {
            Item item = BuiltInRegistries.ITEM.getValue(RocksMain.id(type.getName()));
            registerParentedItemModel(itemModelGenerator, item, getItemId("rock_base"), type.getStoneBlock());

            if (type != RockType.GRAVEL) {
                Item splitter = BuiltInRegistries.ITEM.getValue(RocksMain.id(type.getFragment().getName()));
                registerParentedItemModel(itemModelGenerator, splitter, getItemId("splitter_base"), type.getFragment().getStoneBlock());
            }
        }
        for (StickType type : StickType.values()) {
            Item item = BuiltInRegistries.ITEM.getValue(RocksMain.id(type.getName()+"_stick"));
            registerParentedItemModel(itemModelGenerator, item, getItemId("stick_base"), type.getBaseBlock());
        }
        itemModelGenerator.declareCustomModelItem(RocksMain.Geyser.asItem());
        itemModelGenerator.declareCustomModelItem(RocksMain.NetherGeyser.asItem());
        registerStarfishItemVariations(itemModelGenerator, RocksMain.Starfish);
        itemModelGenerator.declareCustomModelItem(RocksMain.Seashell.asItem());
        itemModelGenerator.declareCustomModelItem(RocksMain.Pinecone.asItem());
    }
    public static void registerParentedItemModel(ItemModelGenerators modelGenerator, Item item, Identifier parentId, Block textureSource) {
        TextureMapping textureMap = TextureMapping.singleSlot(ZERO_TEXTURE_KEY, TextureMapping.getBlockTexture(textureSource));

        Identifier itemModel = getSimpleParentModel(parentId, "").create(item, textureMap, modelGenerator.modelOutput);
        modelGenerator.itemModelOutput.accept(item, ItemModelUtils.plainModel(itemModel));
    }
    public final void registerStarfishItemVariations(ItemModelGenerators modelGenerator, Block starfish) {
        Map<StarfishVariation, ItemModel.Unbaked> variantMap = new HashMap<>();
        for (StarfishVariation variation : StarfishVariation.values()) {
            variantMap.put(variation, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(starfish.asItem(), "_"+variation.toString())));
        }
        modelGenerator.itemModelOutput.accept(starfish.asItem(), ItemModelUtils.selectBlockItemProperty(RocksMain.STARFISH_VARIATION, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(starfish.asItem())), variantMap));
    }

    public static MultiVariant getRandomRotationWeightedVariant(Identifier modelId) {
        WeightedList.Builder<Variant> list = WeightedList.builder();
        for (Quadrant rotation : Quadrant.values()) {
            Variant rotatedVariant = new Variant(modelId, Variant.SimpleModelState.DEFAULT.withY(rotation));
            list.add(rotatedVariant);
        }
        return new MultiVariant(list.build());
    }

    private static class RockModel {
        public static void registerBlockModel(BlockModelGenerators modelGenerator, Block rockBlock, Block textureSource) {
            TextureMapping textureMap = TextureMapping.singleSlot(ZERO_TEXTURE_KEY, TextureMapping.getBlockTexture(textureSource));

            Identifier largeRock = getSimpleParentModel(getBlockId("large_rock"), "_large").create(rockBlock, textureMap, modelGenerator.modelOutput);
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_rock"), "_medium").create(rockBlock, textureMap, modelGenerator.modelOutput);
            Identifier smallRock = getSimpleParentModel(getBlockId("small_rock"), "_small").create(rockBlock, textureMap, modelGenerator.modelOutput);
            Identifier tinyRock = getSimpleParentModel(getBlockId("tiny_rock"), "_tiny").create(rockBlock, textureMap, modelGenerator.modelOutput);
            modelGenerator.blockStateOutput.accept(createBlockState(rockBlock, new Identifier[]{largeRock, mediumRock, smallRock, tinyRock}));
        }

        private static BlockModelDefinitionGenerator createBlockState(Block rockBlock, Identifier[] modelIds) {
            return MultiVariantGenerator.dispatch(rockBlock)
                    .with(PropertyDispatch.initial(RocksMain.ROCK_VARIATION)
                            .generate(variation -> getRandomRotationWeightedVariant(modelIds[3 - variation.ordinal()]))
                    );
        }
    }
    private static class StickModel {
        public static void registerBlockModel(BlockModelGenerators modelGenerator, Block stickBlock, Block textureSource) {
            TextureMapping textureMap = TextureMapping.singleSlot(ZERO_TEXTURE_KEY, TextureMapping.getBlockTexture(textureSource));

            Identifier largeRock = getSimpleParentModel(getBlockId("large_stick"), "_large").create(stickBlock, textureMap, modelGenerator.modelOutput);
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_stick"), "_medium").create(stickBlock, textureMap, modelGenerator.modelOutput);
            Identifier smallRock = getSimpleParentModel(getBlockId("small_stick"), "_small").create(stickBlock, textureMap, modelGenerator.modelOutput);
            modelGenerator.blockStateOutput.accept(createBlockState(stickBlock, new Identifier[]{largeRock, mediumRock, smallRock}));
        }

        private static BlockModelDefinitionGenerator createBlockState(Block stickBlock, Identifier[] modelIds) {
            return MultiVariantGenerator.dispatch(stickBlock)
                    .with(PropertyDispatch.initial(RocksMain.STICK_VARIATION)
                            .generate(variation -> getRandomRotationWeightedVariant(modelIds[2 - variation.ordinal()]))
                    );
        }
    }
}
//~}