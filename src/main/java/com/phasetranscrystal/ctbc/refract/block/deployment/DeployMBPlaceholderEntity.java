package com.phasetranscrystal.ctbc.refract.block.deployment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class DeployMBPlaceholderEntity extends BlockEntity {
    public DeployMBPlaceholderEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ((DeployMBPlaceholder) getBlockState().getBlock()).findBelonging(level, worldPosition, getBlockState())
                .map(level::getBlockEntity)
                .map(e -> e instanceof DeployMBControllerEntity entity ? entity : null)
                .map(e -> e.getCapabilityTransmit(cap, worldPosition, side))
                .orElse(LazyOptional.empty());
    }


}
