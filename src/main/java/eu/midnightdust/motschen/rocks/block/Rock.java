package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
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

public class Rock extends Block {

    private static final VoxelShape SHAPE;
    private static final VoxelShape SHAPE_LARGE;
    private static final EnumProperty<RockVariation> ROCK_VARIATION = RocksMain.ROCK_VARIATION;

    public Rock() {
        super(AbstractBlock.Settings.copy(Blocks.POPPY).nonOpaque().dynamicBounds().sounds(BlockSoundGroup.STONE));
        this.setDefaultState(this.stateManager.getDefaultState().with(ROCK_VARIATION, RockVariation.TINY));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return Objects.requireNonNull(super.getPlacementState(itemPlacementContext))
                .with(ROCK_VARIATION, RockVariation.values()[itemPlacementContext.getWorld().random.nextBetween(0, 3)]);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.isCreative()) {
            world.setBlockState(pos, state.with(ROCK_VARIATION, state.get(ROCK_VARIATION).next()));
            return ActionResult.SUCCESS;
        }
        else return ActionResult.FAIL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROCK_VARIATION);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return state.get(ROCK_VARIATION).equals(RockVariation.LARGE) ? SHAPE_LARGE : SHAPE;
    }
    static {
        SHAPE = createCuboidShape(0, 0, 0, 16, 2, 16);
        SHAPE_LARGE = createCuboidShape(0, 0, 0, 16, 3, 16);
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
