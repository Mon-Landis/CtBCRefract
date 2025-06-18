package com.phasetranscrystal.ctbc.refract.deployment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class DeployMBEntityPlaceholder extends DeployMBPlaceholder implements EntityBlock {
    public DeployMBEntityPlaceholder(Properties pProperties) {
        super(pProperties.pushReaction(PushReaction.BLOCK));
    }

    @Override
    public abstract @Nullable DeployMBPlaceholderEntity newBlockEntity(BlockPos pPos, BlockState pState);

    @Override
    public Optional<BlockPos> tryFindBelonging(BlockGetter level, BlockPos findingPos) {
        return ((DeployMBPlaceholderEntity) level.getBlockEntity(findingPos)).getBelongingPos();
    }
}
