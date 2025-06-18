package com.phasetranscrystal.ctbc.refract.deployment;

import com.phasetranscrystal.ctbc.refract.helper.BFSBlockPosIterator;
import com.phasetranscrystal.ctbc.refract.helper.BoundingBoxHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.function.Function;

public abstract class RectangleDeployMBController extends DeployMBController implements IBoundingBoxProvider {
    public static final Direction[] DIRECTIONS = Direction.values();

    protected RectangleDeployMBController(Properties pProperties) {
        super(pProperties);
    }

    public abstract BoundingBox getRegion();

    public BoundingBox getRegion(Direction pDirection) {
        return BoundingBoxHelper.rotateBox(getRegion(), pDirection);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return getRegion();
    }

    @Override
    protected boolean isRegionPlaceable(Level gameLevel, BlockPos pos, BlockPlaceContext context) {
        return BlockPos.betweenClosedStream(getRegion(context.getHorizontalDirection().getOpposite()))
                .map(pos::offset)
                .map(gameLevel::getBlockState)
                .allMatch(BlockStateBase::canBeReplaced);
    }

    @Override
    protected void placeBlocks(Level level, BlockState state, BlockPos pos) {
        Direction d = state.getOptionalValue(BlockStateProperties.HORIZONTAL_FACING).orElse(Direction.SOUTH);
        BFSBlockPosIterator.streamBlocksBFS(getRegion(d)).forEach((pos1) -> {
            if (!createBlockAt(level, pos, BoundingBoxHelper.rerotatePos(pos1, d), pos1.offset(pos))) {
                for (Direction direction : DIRECTIONS) {
                    if (level.getBlockState(pos1.relative(direction)).getBlock() instanceof IBelongingFinder finder &&
                            finder.getBelongingClass() == this.getBelongingClass()) {
                        level.setBlockAndUpdate(pos1.offset(pos.getX(), pos.getY(), pos.getX()).relative(direction), getPlaceholderBlock(level, pos, pos1)
                                .setValue(DeployMBPlaceholder.BELONG_TRACKER, direction));
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void destruct(LevelAccessor level, BlockState state, BlockPos pos) {
        BlockPos.betweenClosedStream(getRegion(state.getOptionalValue(BlockStateProperties.HORIZONTAL_FACING).orElse(Direction.SOUTH)))
                .map(pos::offset)
                .filter(p -> level.getBlockState(pos).getBlock() instanceof IBelongingFinder finder && finder.getBelongingClass() == this.getBelongingClass())
                .forEach(p -> level.destroyBlock(p, false));
    }

    // @return is consumed
    protected abstract boolean createBlockAt(Level level, BlockPos controllerPos, BlockPos directionalRelativePos, BlockPos targetPos);


}
