package com.phasetranscrystal.ctbc.refract.deployment;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class DeployMBController extends BaseEntityBlock implements IWrenchable, IBelongingFinder {

    protected DeployMBController(Properties pProperties) {
        super(pProperties.pushReaction(PushReaction.BLOCK));
    }

    @Override
    public abstract @Nullable DeployMBControllerEntity newBlockEntity(BlockPos pPos, BlockState pState);

    protected abstract boolean isRegionPlaceable(Level gameLevel, BlockPos pos, BlockPlaceContext context);

    protected abstract void placeBlocks(Level level, BlockState state, BlockPos pos);

    protected abstract void destruct(LevelAccessor level, BlockState state, BlockPos pos);

    //please ensure the root block is child class of BaseDeployMBMPlaceholder
    public abstract BlockState getPlaceholderBlock(LevelAccessor level, BlockPos controllerPos, BlockPos relativePos);

    @Override
    public Optional<BlockPos> findBelonging(BlockGetter level, BlockPos pos, BlockState state) {
        return Optional.of(pos);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
        destruct(pLevel, pState, pPos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return isRegionPlaceable(pContext.getLevel(), pContext.getClickedPos().relative(pContext.getClickedFace()), pContext) ?
                super.getStateForPlacement(pContext) : null;
    }
}
