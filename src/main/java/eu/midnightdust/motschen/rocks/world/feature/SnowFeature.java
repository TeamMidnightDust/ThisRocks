package eu.midnightdust.motschen.rocks.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
//? if <=26.2 {
/*import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class SnowFeature extends Feature<ProbabilityFeatureConfiguration> {
    WeightedStateProvider provider;

    public SnowFeature(Codec<ProbabilityFeatureConfiguration> codec, WeightedStateProvider weightedBlockStateProvider) {
        super(codec);
        provider = weightedBlockStateProvider;
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
*///?} else {
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SnowFeature(float probability, Holder<BlockStateProvider> provider) implements Feature {
    public static final MapCodec<SnowFeature> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(SnowFeature::probability),
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(SnowFeature::provider)
            ).apply(i, SnowFeature::new)
    );

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel structureWorldAccess, ChunkGenerator chunkGenerator, RandomSource random, BlockPos blockPos) {
//?}
        boolean bl = false;
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
        int chance = random.nextInt(8);

        if (structureWorldAccess.getBlockState(blockPos2).is(Blocks.SNOW) && chance == 1) {

            BlockState blockState = this.provider/*? if >= 26.3 {*/.value()/*?}*/.getState(/*? if >= 26.1 {*/ structureWorldAccess,/*?}*/ random,blockPos);

            if (blockState.canSurvive(structureWorldAccess, blockPos2)) {
                structureWorldAccess.setBlock(blockPos2, blockState, 1);
                if (structureWorldAccess.getBlockState(blockPos2.below()) == Blocks.GRASS_BLOCK.defaultBlockState()) {
                    structureWorldAccess.setBlock(blockPos2.below(), Blocks.GRASS_BLOCK.defaultBlockState().setValue(BlockStateProperties.SNOWY, true), 1);
                }

                bl = true;
            }
        }

        return bl;
    }
}
