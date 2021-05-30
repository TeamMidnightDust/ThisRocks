package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
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

public class Starfish extends Block implements Waterloggable {

    private static final VoxelShape SHAPE;
    private static final EnumProperty<StarfishVariation> STARFISH_VARIATION = RocksMain.STARFISH_VARIATION;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public Starfish() {
        super(FabricBlockSettings.copy(Blocks.POPPY).nonOpaque().sounds(BlockSoundGroup.CORAL));
        this.setDefaultState(this.stateManager.getDefaultState().with(STARFISH_VARIATION, StarfishVariation.RED).with(WATERLOGGED, false));
    }

    @Override
    public FluidState getFluidState(BlockState blockState_1) {
        return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(true) : super.getFluidState(blockState_1);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        ItemStack stack = itemPlacementContext.getStack();
        StarfishVariation variation = StarfishVariation.RED;
        if (stack.getTag() != null) {
            var optionalVariation = STARFISH_VARIATION.parse(stack.getTag().getString("variation"));
            if (optionalVariation.isPresent()) variation = optionalVariation.get();
        }
        FluidState fluidState = itemPlacementContext.getWorld().getFluidState(itemPlacementContext.getBlockPos());
        return Objects.requireNonNull(super.getPlacementState(itemPlacementContext))
                .with(STARFISH_VARIATION, variation).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putString("variation", state.get(STARFISH_VARIATION).asString());
        LOGGER.info(state.get(STARFISH_VARIATION).asString());
        return stack;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isCreative()) {
            if (state.get(STARFISH_VARIATION) == StarfishVariation.RED) {
                world.setBlockState(pos, state.with(STARFISH_VARIATION, StarfishVariation.PINK));
            }
            if (state.get(STARFISH_VARIATION) == StarfishVariation.PINK) {
                world.setBlockState(pos, state.with(STARFISH_VARIATION, StarfishVariation.ORANGE));
            }
            if (state.get(STARFISH_VARIATION) == StarfishVariation.ORANGE) {
                world.setBlockState(pos, state.with(STARFISH_VARIATION, StarfishVariation.RED));
            }
            return ActionResult.SUCCESS;
        }
        else return ActionResult.FAIL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STARFISH_VARIATION,WATERLOGGED);
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
}
