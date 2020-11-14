package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.block.blockentity.NetherGeyserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class NetherGeyser extends Block implements BlockEntityProvider {

    private static final VoxelShape SHAPE;
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public NetherGeyser() {
        super(FabricBlockSettings.copy(Blocks.STONE).strength(10).noCollision().nonOpaque().sounds(BlockSoundGroup.STONE));
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false));
    }
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new NetherGeyserBlockEntity();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return super.getPlacementState(itemPlacementContext)
                .with(ACTIVE, false);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    static {
        VoxelShape shape = createCuboidShape(5, 0, 5, 11, 1, 11);

        SHAPE = shape;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world,pos,Direction.UP);
    }
}
