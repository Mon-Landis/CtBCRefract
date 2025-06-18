package com.phasetranscrystal.ctbc.refract.deployment;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;

import java.util.Optional;

public abstract class DeployMBPlaceholder extends Block implements IWrenchable, IBelongingFinder {
    public static final DirectionProperty BELONG_TRACKER = DirectionProperty.create("belong_tracker", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    public DeployMBPlaceholder(Properties pProperties) {
        super(pProperties.pushReaction(PushReaction.BLOCK));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BELONG_TRACKER);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return findBelonging(level, pos, state).map(p -> level.getBlockState(p).getCloneItemStack(target, level, p, player)).orElse(ItemStack.EMPTY);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return super.rotate(state, level, pos, direction).setValue(BELONG_TRACKER, direction.rotate(state.getValue(BELONG_TRACKER)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return super.mirror(pState, pMirror).setValue(BELONG_TRACKER, pMirror.mirror(pState.getValue(BELONG_TRACKER)));
    }

    @Override
    public Optional<BlockPos> findBelonging(BlockGetter level, BlockPos pos, BlockState state) {
        return tryFindBelonging(level, pos.relative(state.getValue(BELONG_TRACKER)));
    }

    public boolean stillValid(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return findBelonging(pLevel, pPos, pState).isPresent();
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
        if (!pLevel.isClientSide) {
            findBelonging(pLevel, pPos, pState).ifPresent(p -> pLevel.destroyBlock(p, !pPlayer.isCreative()));
        }
    }
}
