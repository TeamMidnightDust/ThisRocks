package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.block.blockentity.OverworldGeyserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class OverworldGeyser extends Block implements BlockEntityProvider {

    private static final VoxelShape SHAPE;
    private static final VoxelShape SNOWY_SHAPE;
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    public OverworldGeyser() {
        super(FabricBlockSettings.copy(Blocks.STONE).strength(10).noCollision().nonOpaque().sounds(BlockSoundGroup.STONE));
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false).with(SNOWY, false));
    }
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new OverworldGeyserBlockEntity();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return super.getPlacementState(itemPlacementContext)
                .with(ACTIVE, false).with(SNOWY, false);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE,SNOWY);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        if (state.get(SNOWY) == true) {return SNOWY_SHAPE;}
        else return SHAPE;
    }
    static {
        VoxelShape shape = createCuboidShape(5, 0, 5, 11, 1, 11);
        VoxelShape snow_layer = createCuboidShape(0, 0, 0, 16, 2, 16);
        VoxelShape shape_snow = createCuboidShape(5, 2, 5, 11, 3, 11);
        VoxelShape snowy = VoxelShapes.union(snow_layer, shape_snow);

        SHAPE = shape;
        SNOWY_SHAPE = snowy;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world,pos,Direction.UP);
    }
}
