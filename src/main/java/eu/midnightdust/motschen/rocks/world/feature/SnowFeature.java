package eu.midnightdust.motschen.rocks.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class SnowFeature extends Feature<ProbabilityFeatureConfiguration> {
    WeightedStateProvider weightedBlockStateProvider1;

    public SnowFeature(Codec<ProbabilityFeatureConfiguration> codec, WeightedStateProvider weightedBlockStateProvider) {
        super(codec);
        weightedBlockStateProvider1 = weightedBlockStateProvider;
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        boolean bl = false;
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
        int chance = random.nextInt(8);

        if (structureWorldAccess.getBlockState(blockPos2).is(Blocks.SNOW) && chance == 1) {

            BlockState blockState = weightedBlockStateProvider1.getState(structureWorldAccess, random, blockPos);

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
