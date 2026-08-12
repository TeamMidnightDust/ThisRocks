package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Objects;

public class Rock extends Block {

    private static final VoxelShape SHAPE;
    private static final VoxelShape SHAPE_LARGE;
    private static final EnumProperty<RockVariation> ROCK_VARIATION = RocksMain.ROCK_VARIATION;

    public Rock(Identifier blockId) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).setId(ResourceKey.create(Registries.BLOCK, blockId)).noOcclusion().dynamicShape().sound(SoundType.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(ROCK_VARIATION, RockVariation.TINY));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext itemPlacementContext) {
        return Objects.requireNonNull(super.getStateForPlacement(itemPlacementContext))
                .setValue(ROCK_VARIATION, RockVariation.values()[itemPlacementContext.getLevel().random.nextIntBetweenInclusive(0, 3)]);
    }
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.isCreative()) {
            world.setBlockAndUpdate(pos, state.setValue(ROCK_VARIATION, state.getValue(ROCK_VARIATION).next()));
            return InteractionResult.SUCCESS;
        }
        else return InteractionResult.FAIL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROCK_VARIATION);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        return state.getValue(ROCK_VARIATION).equals(RockVariation.LARGE) ? SHAPE_LARGE : SHAPE;
    }
    static {
        SHAPE = box(0, 0, 0, 16, 2, 16);
        SHAPE_LARGE = box(0, 0, 0, 16, 3, 16);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isFaceSturdy(world,pos,Direction.UP);
    }
    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }
    @Override
    protected boolean propagatesSkylightDown(BlockState state) {return true;}
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {return true;}
}
