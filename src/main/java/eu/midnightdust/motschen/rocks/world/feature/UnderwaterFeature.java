package eu.midnightdust.motschen.rocks.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class UnderwaterFeature extends Feature<ProbabilityConfig> {
    WeightedBlockStateProvider weightedBlockStateProvider1;
    public UnderwaterFeature(Codec<ProbabilityConfig> codec, WeightedBlockStateProvider weightedBlockStateProvider) {
        super(codec);
        weightedBlockStateProvider1 = weightedBlockStateProvider;
    }

    @Override
    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        boolean bl = false;
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
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
