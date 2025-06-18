package com.phasetranscrystal.ctbc.refract.obj;

import com.phasetranscrystal.ctbc.refract.register.BlockEntityRegistry;
import com.phasetranscrystal.ctbc.refract.register.BlockRegistry;
import com.phasetranscrystal.ctbc.refract.deployment.DeployMBControllerEntity;
import com.phasetranscrystal.ctbc.refract.deployment.IBoundingBoxProvider;
import com.phasetranscrystal.ctbc.refract.deployment.RectangleDeployMBController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AssemblyMac extends RectangleDeployMBController {
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BoundingBox REGION = new BoundingBox(-2, 0, -6, 2, 2, 0);

    public AssemblyMac() {
        super(Properties.copy(Blocks.DARK_OAK_WOOD).noOcclusion());
    }

    @Override
    public @Nullable DeployMBControllerEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new Entity(pPos, pState);
    }

    @Override
    public BlockState getPlaceholderBlock(LevelAccessor level, BlockPos controllerPos, BlockPos relativePos) {
        return null;
    }

    @Override
    public BoundingBox getRegion() {
        return REGION;
    }

    @Override
    protected boolean createBlockAt(Level level, BlockPos controllerPos, BlockPos relativePos, BlockPos targetPos) {
        return false;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState s = super.getStateForPlacement(pContext);
        return s != null ? s.setValue(HORIZONTAL_FACING, pContext.getHorizontalDirection().getOpposite()) : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HORIZONTAL_FACING);
    }

    @Override
    public Class<? extends Block> getBelongingClass() {
        return this.getClass();
    }

    public static class Entity extends DeployMBControllerEntity implements IBoundingBoxProvider {
        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(BlockEntityRegistry.ASSEMBLY_MAC.get(), pPos, pBlockState);
        }

        public Entity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
            super(type, pPos, pBlockState);
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapabilityTransmit(Capability<T> cap, BlockPos requiredPos, @Nullable Direction side) {
            return LazyOptional.empty();
        }

        @Override
        public List<ItemStack> onDestroy() {
            return List.of();
        }

        @Override
        public BoundingBox getBoundingBox() {
            return ((RectangleDeployMBController) getBlockState().getBlock())
                    .getRegion(getBlockState().getOptionalValue(BlockStateProperties.HORIZONTAL_FACING).orElse(Direction.SOUTH))
                    .moved(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
        }
    }
}
