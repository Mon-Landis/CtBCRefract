package com.phasetranscrystal.ctbc.refract.deployment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class DeployMBPlaceholderEntity extends BlockEntity {
    private Optional<BlockPos> belongingPos = Optional.empty();

    public DeployMBPlaceholderEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void bindBelongingPos(BlockPos pPos) {
        this.belongingPos = Optional.of(pPos);
        setChanged();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ((DeployMBPlaceholder) getBlockState().getBlock()).findBelonging(level, worldPosition, getBlockState())
                .map(level::getBlockEntity)
                .map(e -> e instanceof DeployMBControllerEntity entity ? entity : null)
                .map(e -> e.getCapabilityTransmit(cap, worldPosition, side))
                .orElse(LazyOptional.empty());
    }

    public Optional<BlockPos> getBelongingPos() {
        return belongingPos;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        belongingPos.ifPresent(e -> pTag.putLong("belonging",e.asLong()));
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains("belonging")) {
            belongingPos = Optional.of(BlockPos.of(pTag.getLong("belonging")));
        }
    }
}
