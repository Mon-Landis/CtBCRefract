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

import javax.swing.text.html.parser.Entity;
import java.util.List;

public abstract class DeployMBControllerEntity extends BlockEntity {
    public DeployMBControllerEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public abstract @NotNull <T> LazyOptional<T> getCapabilityTransmit(Capability<T> cap, BlockPos requiredPos, @Nullable Direction side);

    public abstract List<ItemStack> onDestroy();
}
