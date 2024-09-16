package eu.midnightdust.motschen.rocks.block.polymer;

import eu.midnightdust.motschen.rocks.block.Seashell;
import eu.midnightdust.motschen.rocks.block.polymer.model.ItemDisplaySeashellModel;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;

public class SeashellPolymer extends Seashell implements PolymerBlock, PolymerTexturedBlock, BlockWithElementHolder {
    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        if (state.get(WATERLOGGED)) return PolyUtil.PASSABLE_WATERLOGGED_BLOCK;
        else return PolyUtil.SMALL_BLOCK;
    }
    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayerEntity player) {
        return hasModOnClient(player) ? state : getPolymerBlockState(state);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, ServerPlayerEntity player) {
        return hasModOnClient(player) ? state : Blocks.WHITE_CANDLE.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new ItemDisplaySeashellModel(initialBlockState, pos);
    }

    @Override
    public boolean canSyncRawToClient(@Nullable ServerPlayerEntity player) {
        return hasModOnClient(player);
    }
}
