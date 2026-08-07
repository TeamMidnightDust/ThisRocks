package eu.midnightdust.motschen.rocks.block.polymer;

import eu.midnightdust.motschen.rocks.block.Stick;
import eu.midnightdust.motschen.rocks.block.polymer.model.ItemDisplayStickModel;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;
import eu.pb4.polymer.common.api.PolymerCommonUtils;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;

public class StickPolymer extends Stick implements PolymerBlock, PolymerTexturedBlock, BlockWithElementHolder {
    public StickPolymer(Identifier blockId) {
        super(blockId);
    }

    public BlockState getPolymerBlockState(BlockState state) {
        return PolyUtil.SMALL_BLOCK;
    }
    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return hasModOnClient(PolymerCommonUtils.getPlayer(context)) ? state : getPolymerBlockState(state);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return hasModOnClient(PolymerCommonUtils.getPlayer(context)) ? state : Blocks.OAK_BUTTON.defaultBlockState().setValue(BlockStateProperties.ATTACH_FACE, AttachFace.FLOOR);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        return new ItemDisplayStickModel(initialBlockState, pos);
    }

    @Override
    public boolean canSyncRawToClient(PacketContext context) {
        return hasModOnClient(PolymerCommonUtils.getPlayer(context));
    }
}
