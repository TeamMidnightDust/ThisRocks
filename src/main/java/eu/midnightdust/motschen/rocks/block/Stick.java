package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Objects;

public class Stick extends Block {

    private static final VoxelShape SHAPE;
    private static final EnumProperty<StickVariation> STICK_VARIATION = RocksMain.STICK_VARIATION;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public Stick() {
        super(AbstractBlock.Settings.copy(Blocks.POPPY).nonOpaque().dynamicBounds().sounds(BlockSoundGroup.WOOD));
        this.setDefaultState(this.stateManager.getDefaultState().with(STICK_VARIATION, StickVariation.SMALL).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return Objects.requireNonNull(super.getPlacementState(itemPlacementContext))
                .with(STICK_VARIATION, StickVariation.values()[itemPlacementContext.getWorld().random.nextBetween(0, 2)])
                .with(WATERLOGGED, false);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.isCreative()) {
            world.setBlockState(pos, state.with(STICK_VARIATION, state.get(STICK_VARIATION).next()));
            return ActionResult.SUCCESS;
        }
        else return ActionResult.FAIL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STICK_VARIATION, WATERLOGGED);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    static {
        SHAPE = createCuboidShape(0, 0, 0, 16, 1, 16);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world,pos,Direction.UP);
    }
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {return true;}
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {return true;}
}
