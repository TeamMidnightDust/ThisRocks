package eu.midnightdust.motschen.rocks.block;

import com.mojang.serialization.MapCodec;
import eu.midnightdust.motschen.rocks.block.blockentity.BlockEntityInit;
import eu.midnightdust.motschen.rocks.block.blockentity.OverworldGeyserBlockEntity;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class OverworldGeyser extends BaseEntityBlock implements EntityBlock {

    private static final VoxelShape SHAPE;
    private static final VoxelShape SNOWY_SHAPE;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;

    public OverworldGeyser(Identifier blockId) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, blockId)).strength(10).noCollision().dynamicShape().noOcclusion().sound(SoundType.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false).setValue(SNOWY, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OverworldGeyserBlockEntity(pos, state);
    }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityInit.OVERWORLD_GEYSER_BE, OverworldGeyserBlockEntity::tick);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext itemPlacementContext) {
        return Objects.requireNonNull(super.getStateForPlacement(itemPlacementContext))
                .setValue(ACTIVE, false).setValue(SNOWY, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE,SNOWY);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        if (state.getValue(SNOWY)) {return SNOWY_SHAPE;}
        else return SHAPE;
    }
    static {
        VoxelShape shape = box(5, 0, 5, 11, 1, 11);
        VoxelShape snow_layer = box(0, 0, 0, 16, 2, 16);
        VoxelShape shape_snow = box(5, 2, 5, 11, 3, 11);
        VoxelShape snowy = Shapes.or(snow_layer, shape_snow);

        SHAPE = shape;
        SNOWY_SHAPE = snowy;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isFaceSturdy(world,pos,Direction.UP);
    }
}
