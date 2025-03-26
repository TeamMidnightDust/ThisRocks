package eu.midnightdust.motschen.rocks.block;

import com.mojang.serialization.MapCodec;
import eu.midnightdust.motschen.rocks.block.blockentity.BlockEntityInit;
import eu.midnightdust.motschen.rocks.block.blockentity.OverworldGeyserBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class OverworldGeyser extends BlockWithEntity implements BlockEntityProvider {

    private static final VoxelShape SHAPE;
    private static final VoxelShape SNOWY_SHAPE;
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    public OverworldGeyser(Identifier blockId) {
        super(AbstractBlock.Settings.copy(Blocks.STONE).registryKey(RegistryKey.of(RegistryKeys.BLOCK, blockId)).strength(10).noCollision().dynamicBounds().nonOpaque().sounds(BlockSoundGroup.STONE));
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false).with(SNOWY, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OverworldGeyserBlockEntity(pos, state);
    }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, BlockEntityInit.OVERWORLD_GEYSER_BE, OverworldGeyserBlockEntity::tick);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return Objects.requireNonNull(super.getPlacementState(itemPlacementContext))
                .with(ACTIVE, false).with(SNOWY, false);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE,SNOWY);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        if (state.get(SNOWY)) {return SNOWY_SHAPE;}
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

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world,pos,Direction.UP);
    }
}
