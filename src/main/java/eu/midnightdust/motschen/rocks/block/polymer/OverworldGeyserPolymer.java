package eu.midnightdust.motschen.rocks.block.polymer;

import eu.midnightdust.motschen.rocks.block.OverworldGeyser;
import eu.midnightdust.motschen.rocks.block.polymer.model.ItemDisplayOverworldGeyserModel;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;

public class OverworldGeyserPolymer extends OverworldGeyser implements PolymerBlock, PolymerTexturedBlock, BlockWithElementHolder {
    public OverworldGeyserPolymer(Identifier blockId) {
        super(blockId);
    }

    public BlockState getPolymerBlockState(BlockState state) {
        return state.get(SNOWY) ? Blocks.SNOW.getDefaultState() : PolyUtil.SMALL_BLOCK;
    }
    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayerEntity player) {
        return hasModOnClient(player) ? state : getPolymerBlockState(state);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, ServerPlayerEntity player) {
        return hasModOnClient(player) ? state : Blocks.SNOW.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new ItemDisplayOverworldGeyserModel(initialBlockState, pos);
    }

    @Override
    public boolean canSyncRawToClient(ServerPlayerEntity player) {
        return hasModOnClient(player);
    }
}
