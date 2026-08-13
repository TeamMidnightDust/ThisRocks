package eu.midnightdust.motschen.rocks.block.polymer;

import eu.midnightdust.motschen.rocks.block.Seashell;
import eu.midnightdust.motschen.rocks.block.polymer.model.ItemDisplaySeashellModel;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
//~ if >= 26.1 'xyz.nucleoid.packettweaker.PacketContext' -> 'net.fabricmc.fabric.api.networking.v1.context.PacketContext'
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;

public class SeashellPolymer extends Seashell implements PolymerBlock, PolymerTexturedBlock, BlockWithElementHolder {
    public SeashellPolymer(Identifier blockId) {
        super(blockId);
    }

    public BlockState getPolymerBlockState(BlockState state) {
        if (state.getValue(WATERLOGGED)) return PolyUtil.PASSABLE_WATERLOGGED_BLOCK;
        else return PolyUtil.SMALL_BLOCK;
    }
    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return hasModOnClient(context) ? state : getPolymerBlockState(state);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        //~ if >= 26.2 'WHITE_CANDLE' -> 'CANDLE'
        return hasModOnClient(context) ? state : Blocks.CANDLE.defaultBlockState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        return new ItemDisplaySeashellModel(initialBlockState, pos);
    }

    @Override
    public boolean canSyncRawToClient(PacketContext context) {
        return hasModOnClient(context);
    }
}
