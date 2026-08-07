package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import com.mojang.math.Quadrant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.*;

public class Models extends FabricModelProvider {
    // ------------------------------------------------------------------
    // 26.2 API gap workaround
    //
    // Two things this file needs are no longer reachable through public
    // API in 26.2, and Fabric API's own client-datagen mixins
    // (ModelProviderMixin / ModelProviderBlockStateGeneratorCollectorMixin /
    // ModelProviderItemInfoCollectorMixin, in fabric-data-generation-api-v1)
    // do not restore them either -- verified by javap against both the
    // merged Minecraft jar and the fabric-data-generation-api-v1 jar:
    //
    //   1. TextureSlot no longer exposes a public way to create a
    //      slot with an arbitrary id. Our parent models
    //      (large_rock.json, rock_base.json, ...) declare a texture
    //      variable literally named "0" (see src/main/resources/assets/
    //      rocks/models/...), so we need a TextureSlot whose id is "0".
    //      TextureSlot.create(String) still exists and does exactly
    //      that, but it is private with no public equivalent.
    //   2. BlockModelGenerators/ItemModelGenerators no longer expose the
    //      blockStateOutput/modelOutput/itemModelOutput collector fields
    //      that FabricModelProvider subclasses used to reach directly
    //      (blockStateCollector/modelCollector/output in the 1.21.11
    //      Yarn mapping). They are still there, still the exact same
    //      types, just private with no accessor.
    //
    // Both are reached here via reflection on the real Mojang objects we
    // are handed, so every byte actually written still comes from
    // Mojang's own serialization -- reflection is only used to obtain a
    // reference, not to fabricate behavior. This is a deliberate,
    // reviewed workaround; see the task 6 report for details.
    private static TextureSlot createTextureSlot(String id) {
        try {
            Method create = TextureSlot.class.getDeclaredMethod("create", String.class);
            create.setAccessible(true);
            return (TextureSlot) create.invoke(null, id);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unable to construct custom TextureSlot \""+id+"\" via reflection", e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <T> T readOutputField(Object target, Class<?> owner, String fieldName) {
        try {
            Field field = owner.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unable to read "+owner.getSimpleName()+"#"+fieldName+" via reflection", e);
        }
    }
    private static BiConsumer<Identifier, ModelInstance> modelOutputOf(BlockModelGenerators modelGenerator) {
        return readOutputField(modelGenerator, BlockModelGenerators.class, "modelOutput");
    }
    private static Consumer<BlockModelDefinitionGenerator> blockStateOutputOf(BlockModelGenerators modelGenerator) {
        return readOutputField(modelGenerator, BlockModelGenerators.class, "blockStateOutput");
    }
    private static BiConsumer<Identifier, ModelInstance> modelOutputOf(ItemModelGenerators modelGenerator) {
        return readOutputField(modelGenerator, ItemModelGenerators.class, "modelOutput");
    }
    private static ItemModelOutput itemModelOutputOf(ItemModelGenerators modelGenerator) {
        return readOutputField(modelGenerator, ItemModelGenerators.class, "itemModelOutput");
    }
    // ------------------------------------------------------------------

    public static final TextureSlot ZERO_TEXTURE_KEY = createTextureSlot("0");
    public Models(FabricPackOutput output) {
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
        registerBasicItemModel(itemModelGenerator, RocksMain.Geyser.asItem());
        registerBasicItemModel(itemModelGenerator, RocksMain.NetherGeyser.asItem());
        registerStarfishItemVariations(itemModelGenerator, RocksMain.Starfish);
        registerBasicItemModel(itemModelGenerator, RocksMain.Seashell.asItem());
        registerBasicItemModel(itemModelGenerator, RocksMain.Pinecone.asItem());
    }
    public static void registerParentedItemModel(ItemModelGenerators modelGenerator, Item item, Identifier parentId, Block textureSource) {
        TextureMapping textureMap = TextureMapping.singleSlot(ZERO_TEXTURE_KEY, TextureMapping.getBlockTexture(textureSource));

        Identifier itemModel = getSimpleParentModel(parentId, "").create(item, textureMap, modelOutputOf(modelGenerator));
        itemModelOutputOf(modelGenerator).accept(item, ItemModelUtils.plainModel(itemModel));
    }
    public static void registerBasicItemModel(ItemModelGenerators modelGenerator, Item item) {
        itemModelOutputOf(modelGenerator).accept(item, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)));
    }
    public final void registerStarfishItemVariations(ItemModelGenerators modelGenerator, Block starfish) {
        Map<StarfishVariation, ItemModel.Unbaked> variantMap = new HashMap<>();
        for (StarfishVariation variation : StarfishVariation.values()) {
            variantMap.put(variation, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(starfish.asItem()).withSuffix("_"+variation.toString())));
        }
        itemModelOutputOf(modelGenerator).accept(starfish.asItem(), ItemModelUtils.selectBlockItemProperty(RocksMain.STARFISH_VARIATION, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(starfish.asItem())), variantMap));
    }

    public static MultiVariant getRandomRotationWeightedVariant(Identifier modelId) {
        WeightedList.Builder<Variant> list = WeightedList.builder();
        for (Quadrant rotation : Quadrant.values()) {
            Variant rotatedVariant = new Variant(modelId).withYRot(rotation);
            list.add(rotatedVariant);
        }
        return new MultiVariant(list.build());
    }

    private static class RockModel {
        public static void registerBlockModel(BlockModelGenerators modelGenerator, Block rockBlock, Block textureSource) {
            TextureMapping textureMap = TextureMapping.singleSlot(ZERO_TEXTURE_KEY, TextureMapping.getBlockTexture(textureSource));

            Identifier largeRock = getSimpleParentModel(getBlockId("large_rock"), "_large").create(rockBlock, textureMap, modelOutputOf(modelGenerator));
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_rock"), "_medium").create(rockBlock, textureMap, modelOutputOf(modelGenerator));
            Identifier smallRock = getSimpleParentModel(getBlockId("small_rock"), "_small").create(rockBlock, textureMap, modelOutputOf(modelGenerator));
            Identifier tinyRock = getSimpleParentModel(getBlockId("tiny_rock"), "_tiny").create(rockBlock, textureMap, modelOutputOf(modelGenerator));
            blockStateOutputOf(modelGenerator).accept(createBlockState(rockBlock, new Identifier[]{largeRock, mediumRock, smallRock, tinyRock}));
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

            Identifier largeRock = getSimpleParentModel(getBlockId("large_stick"), "_large").create(stickBlock, textureMap, modelOutputOf(modelGenerator));
            Identifier mediumRock = getSimpleParentModel(getBlockId("medium_stick"), "_medium").create(stickBlock, textureMap, modelOutputOf(modelGenerator));
            Identifier smallRock = getSimpleParentModel(getBlockId("small_stick"), "_small").create(stickBlock, textureMap, modelOutputOf(modelGenerator));
            blockStateOutputOf(modelGenerator).accept(createBlockState(stickBlock, new Identifier[]{largeRock, mediumRock, smallRock}));
        }

        private static BlockModelDefinitionGenerator createBlockState(Block stickBlock, Identifier[] modelIds) {
            return MultiVariantGenerator.dispatch(stickBlock)
                    .with(PropertyDispatch.initial(RocksMain.STICK_VARIATION)
                            .generate(variation -> getRandomRotationWeightedVariant(modelIds[2 - variation.ordinal()]))
                    );
        }
    }
}
