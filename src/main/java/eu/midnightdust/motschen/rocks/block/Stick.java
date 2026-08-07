package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;

import java.util.Objects;

public class Stick extends Block {

    private static final VoxelShape SHAPE;
    private static final EnumProperty<StickVariation> STICK_VARIATION = RocksMain.STICK_VARIATION;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public Stick(Identifier blockId) {
        super(BlockBehaviour.Properties.copy(Blocks.POPPY).registryKey(ResourceKey.of(Registries.BLOCK, blockId)).nonOpaque().dynamicBounds().sounds(SoundType.WOOD));
        this.setDefaultState(this.stateManager.getDefaultState().with(STICK_VARIATION, StickVariation.SMALL).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext itemPlacementContext) {
        return Objects.requireNonNull(super.getPlacementState(itemPlacementContext))
                .with(STICK_VARIATION, StickVariation.values()[itemPlacementContext.getWorld().random.nextBetween(0, 2)])
                .with(WATERLOGGED, false);
    }
    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.isCreative()) {
            world.setBlockState(pos, state.with(STICK_VARIATION, state.get(STICK_VARIATION).next()));
            return InteractionResult.SUCCESS;
        }
        else return InteractionResult.FAIL;
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STICK_VARIATION, WATERLOGGED);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    static {
        SHAPE = createCuboidShape(0, 0, 0, 16, 1, 16);
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world,pos,Direction.UP);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }
    @Override
    protected boolean isTransparent(BlockState state) {return true;}
    @Override
    protected boolean canReplace(BlockState state, BlockPlaceContext context) {return true;}
}
