package eu.midnightdust.motschen.rocks.block.polymer;

import eu.midnightdust.motschen.rocks.block.NetherGeyser;
import eu.midnightdust.motschen.rocks.block.polymer.model.ItemDisplayNetherGeyserModel;
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
import xyz.nucleoid.packettweaker.PacketContext;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;

public class NetherGeyserPolymer extends NetherGeyser implements PolymerBlock, PolymerTexturedBlock, BlockWithElementHolder {
    public NetherGeyserPolymer(Identifier blockId) {
        super(blockId);
    }

    public BlockState getPolymerBlockState(BlockState state) {
        return PolyUtil.SMALL_BLOCK;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return context != null && hasModOnClient(context.getPlayer()) ? state : getPolymerBlockState(state);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return context != null && hasModOnClient(context.getPlayer()) ? state : Blocks.NETHERRACK.defaultBlockState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        return new ItemDisplayNetherGeyserModel(initialBlockState, pos);
    }

    @Override
    public boolean canSyncRawToClient(PacketContext context) {
        return context != null && hasModOnClient(context.getPlayer());
    }

}
