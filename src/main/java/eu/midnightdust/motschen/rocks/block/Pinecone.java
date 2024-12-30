package eu.midnightdust.motschen.rocks.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class Pinecone extends Block {

    private static final VoxelShape SHAPE;

    public Pinecone(Identifier blockId) {
        super(AbstractBlock.Settings.copy(Blocks.POPPY).registryKey(RegistryKey.of(RegistryKeys.BLOCK, blockId)).nonOpaque().dynamicBounds().sounds(BlockSoundGroup.WOOD));
        this.setDefaultState(this.stateManager.getDefaultState());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    static {
        SHAPE = createCuboidShape(0, 0, 0, 16, 3, 16);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world,pos,Direction.UP);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }
    @Override
    protected boolean isTransparent(BlockState state) {return true;}
    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {return true;}
}
