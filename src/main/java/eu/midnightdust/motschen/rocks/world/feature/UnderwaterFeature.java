package eu.midnightdust.motschen.rocks.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class UnderwaterFeature extends Feature<ProbabilityFeatureConfiguration> {
    WeightedStateProvider weightedBlockStateProvider1;
    public UnderwaterFeature(Codec<ProbabilityFeatureConfiguration> codec, WeightedStateProvider weightedBlockStateProvider) {
        super(codec);
        weightedBlockStateProvider1 = weightedBlockStateProvider;
    }

    @Override
    public boolean generate(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        RandomSource random = context.getRandom();
        WorldGenLevel structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        boolean bl = false;
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getTopY(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);

        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {

            BlockState blockState = weightedBlockStateProvider1.get(random,blockPos);

            if (blockState.canPlaceAt(structureWorldAccess, blockPos2)) {
                structureWorldAccess.setBlockState(blockPos2, blockState, 2);

                bl = true;
            }
        }

        return bl;
    }
}
