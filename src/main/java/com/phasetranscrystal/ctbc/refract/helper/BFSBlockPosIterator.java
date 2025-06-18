package com.phasetranscrystal.ctbc.refract.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BFSBlockPosIterator {

    /**
     * 生成按曼哈顿距离逐渐远离原点的方块位置流
     * @param box 必须包含(0,0,0)的边界框
     * @return 按曼哈顿距离顺序的BlockPos流
     */
    public static Stream<BlockPos> streamBlocksBFS(BoundingBox box) {
        return StreamSupport.stream(new BFSSpliterator(box), false);
    }

    public static Stream<BlockPos> streamBlocksBFSDedicated(BoundingBox box) {
        return StreamSupport.stream(new BFSSpliterator(box), false);
    }

    private static class BFSSpliterator implements Spliterator<BlockPos> {
        private final Queue<BlockPos> queue = new ArrayDeque<>();
        private final Set<BlockPos> visited = new HashSet<>();
        private final BoundingBox box;

        public BFSSpliterator(BoundingBox box) {
            this.box = box;
            // 确保起始点(0,0,0)在边界框内
            BlockPos start = BlockPos.ZERO;
            if (box.isInside(start.getX(), start.getY(), start.getZ())) {
                queue.add(start);
                visited.add(start);
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super BlockPos> action) {
            if (queue.isEmpty()) return false;

            BlockPos current = queue.poll();
            action.accept(current);

            // 遍历所有六个方向
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);

                // 检查是否在边界框内且未访问过
                if (box.isInside(neighbor.getX(), neighbor.getY(), neighbor.getZ())
                        && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
            return true;
        }

        @Override
        public Spliterator<BlockPos> trySplit() {
            return null; // 不支持并行处理
        }

        @Override
        public long estimateSize() {
            return (long) box.getXSpan() * box.getYSpan() * box.getZSpan(); // 大小未知
        }

        @Override
        public int characteristics() {
            return ORDERED | DISTINCT | NONNULL;
        }
    }
}