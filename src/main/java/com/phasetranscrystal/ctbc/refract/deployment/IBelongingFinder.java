package com.phasetranscrystal.ctbc.refract.deployment;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public interface IBelongingFinder {
    Optional<BlockPos> findBelonging(BlockGetter level, BlockPos pos, BlockState state);

    Class<? extends Block> getBelongingClass();

    default Optional<BlockPos> tryFindBelonging(BlockGetter level, BlockPos findingPos) {
        return (level.getBlockState(findingPos) instanceof IBelongingFinder finder && finder.getBelongingClass() == this.getBelongingClass()) ?
                finder.findBelonging(level, findingPos, level.getBlockState(findingPos)) : Optional.empty();
    }
}
