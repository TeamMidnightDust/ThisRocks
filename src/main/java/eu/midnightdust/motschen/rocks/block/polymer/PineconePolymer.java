package eu.midnightdust.motschen.rocks.block.polymer;

import eu.midnightdust.motschen.rocks.block.Pinecone;
import eu.midnightdust.motschen.rocks.block.polymer.model.ItemDisplayPineconeModel;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;

public class PineconePolymer extends Pinecone implements PolymerBlock, PolymerTexturedBlock, BlockWithElementHolder {
    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return PolyUtil.SMALL_BLOCK;
    }
    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayerEntity player) {
        return hasModOnClient(player) ? state : getPolymerBlockState(state);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, ServerPlayerEntity player) {
        return hasModOnClient(player) ? state : Blocks.SPRUCE_BUTTON.getDefaultState().with(Properties.BLOCK_FACE, BlockFace.FLOOR);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new ItemDisplayPineconeModel(initialBlockState, pos);
    }

    @Override
    public boolean canSyncRawToClient(@Nullable ServerPlayerEntity player) {
        return hasModOnClient(player);
    }
}
