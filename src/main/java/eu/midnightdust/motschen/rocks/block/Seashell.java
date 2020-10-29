package eu.midnightdust.motschen.rocks.block;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import net.minecraft.world.WorldView;

public class Seashell extends Block {

    private static final VoxelShape SHAPE;
    private static final EnumProperty<SeashellVariation> SEASHELL_VARIATION = RocksMain.SEASHELL_VARIATION;

    public Seashell() {
        super(FabricBlockSettings.copy(Blocks.POPPY).nonOpaque().sounds(BlockSoundGroup.STONE));
        this.setDefaultState(this.stateManager.getDefaultState().with(SEASHELL_VARIATION, SeashellVariation.PINK));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return super.getPlacementState(itemPlacementContext)
                .with(SEASHELL_VARIATION, SeashellVariation.PINK);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isCreative()) {
            if (state.get(SEASHELL_VARIATION) == SeashellVariation.YELLOW) {
                world.setBlockState(pos, state.with(SEASHELL_VARIATION, SeashellVariation.WHITE));
            }
            if (state.get(SEASHELL_VARIATION) == SeashellVariation.WHITE) {
                world.setBlockState(pos, state.with(SEASHELL_VARIATION, SeashellVariation.PINK));
            }
            if (state.get(SEASHELL_VARIATION) == SeashellVariation.PINK) {
                world.setBlockState(pos, state.with(SEASHELL_VARIATION, SeashellVariation.YELLOW));
            }
            return ActionResult.SUCCESS;
        }
        else return ActionResult.FAIL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SEASHELL_VARIATION);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    static {
        VoxelShape shape = createCuboidShape(0, 0, 0, 16, 3, 16);

        SHAPE = shape;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world,pos,Direction.UP);
    }
}
