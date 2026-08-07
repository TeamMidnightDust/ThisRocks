package eu.midnightdust.motschen.rocks.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;

public class Pinecone extends Block {

    private static final VoxelShape SHAPE;

    public Pinecone(Identifier blockId) {
        super(BlockBehaviour.Properties.copy(Blocks.POPPY).registryKey(ResourceKey.of(Registries.BLOCK, blockId)).nonOpaque().dynamicBounds().sounds(SoundType.WOOD));
        this.setDefaultState(this.stateManager.getDefaultState());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    static {
        SHAPE = createCuboidShape(0, 0, 0, 16, 3, 16);
    }

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
