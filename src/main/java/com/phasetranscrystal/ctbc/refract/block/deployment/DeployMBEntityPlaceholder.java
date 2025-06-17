package com.phasetranscrystal.ctbc.refract.block.deployment;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class DeployMBEntityPlaceholder extends DeployMBPlaceholder implements EntityBlock {
    public static final DirectionProperty BELONG_TRACKER = DirectionProperty.create("belong_tracker", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    public DeployMBEntityPlaceholder(Properties pProperties) {
        super(pProperties.pushReaction(PushReaction.BLOCK));
    }

    @Override
    public abstract @Nullable DeployMBPlaceholderEntity newBlockEntity(BlockPos pPos, BlockState pState);
}
