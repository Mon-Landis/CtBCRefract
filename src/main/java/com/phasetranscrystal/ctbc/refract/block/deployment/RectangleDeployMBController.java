package com.phasetranscrystal.ctbc.refract.block.deployment;

import com.phasetranscrystal.ctbc.refract.block.helper.BFSBlockPosIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public abstract class RectangleDeployMBController extends DeployMBController {
    public static final Direction[] DIRECTIONS = Direction.values();

    protected RectangleDeployMBController(Properties pProperties) {
        super(pProperties);
    }

    public abstract BoundingBox getRegion();

    public BoundingBox getRegion(Direction pDirection) {
        return rotateBoundingBoxHorizonal(getRegion(), pDirection);
    }

    @Override
    protected boolean isRegionPlaceable(Level gameLevel, BlockPos pos) {
        return BlockPos.betweenClosedStream(getRegion())
                .map(gameLevel::getBlockState)
                .allMatch(BlockStateBase::canBeReplaced);
    }

    @Override
    protected void placeBlocks(Level level, BlockState state, BlockPos pos) {
        BFSBlockPosIterator.streamBlocksBFS(getRegion()).forEach((pos1) -> {
            if (!createBlockAt(level, pos, pos1)) {
                for (Direction direction : DIRECTIONS) {
                    if (level.getBlockState(pos1.relative(direction)).getBlock() instanceof IBelongingFinder finder &&
                            finder.getBelongingClass() == this.getBelongingClass()) {
                        level.setBlockAndUpdate(pos1.relative(direction), getPlaceholderBlock(level, pos, pos1)
                                .setValue(DeployMBPlaceholder.BELONG_TRACKER, direction));
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void destruct(LevelAccessor level, BlockState state, BlockPos pos) {
        BlockPos.betweenClosedStream(getRegion())
                .map(pos::offset)
                .filter(p -> level.getBlockState(pos).getBlock() instanceof IBelongingFinder finder && finder.getBelongingClass() == this.getBelongingClass())
                .forEach(p -> level.destroyBlock(p, false));
    }

    // @return is consumed
    protected abstract boolean createBlockAt(Level level, BlockPos controllerPos, BlockPos relativePos);

    public static BoundingBox rotateBoundingBoxHorizonal(BoundingBox box, Direction direction) {
        return BoundingBox.orientBox(0, 0, 0, box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ(), direction);
    }
}
