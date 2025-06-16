package com.phasetranscrystal.ctbc.refract.block.deployment;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class BaseDeployMBMController extends BaseEntityBlock implements IWrenchable {

    protected BaseDeployMBMController(Properties pProperties) {
        super(pProperties.pushReaction(PushReaction.BLOCK));
    }

    protected abstract boolean isRegionPlaceable(Level gameLevel, BlockState state, BlockPos pos);

    protected abstract void placeBlocks(Level level, BlockState state, BlockPos pos);

    protected abstract void destruct(Level level, BlockState state, BlockPos pos);


}
