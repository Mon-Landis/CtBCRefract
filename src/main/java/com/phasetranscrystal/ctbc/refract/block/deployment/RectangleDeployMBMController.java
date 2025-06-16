package com.phasetranscrystal.ctbc.refract.block.deployment;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public abstract class RectangleDeployMBMController extends BaseDeployMBMController{
    protected RectangleDeployMBMController(Properties pProperties) {
        super(pProperties);
    }

    protected abstract AABB getRegion();

    @Override
    protected boolean isRegionPlaceable(Level gameLevel, BlockState state, BlockPos pos) {
        return false;
    }

    @Override
    protected void placeBlocks(Level level, BlockState state, BlockPos pos) {

    }

    @Override
    protected void destruct(Level level, BlockState state, BlockPos pos) {

    }


}
