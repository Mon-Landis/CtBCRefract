package com.phasetranscrystal.ctbc.refract.helper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.phasetranscrystal.ctbc.refract.deployment.IBoundingBoxProvider;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.createmod.catnip.data.Pair;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;


public class BoundingBoxHelper {
    public static class TestingRenderer<T extends BlockEntity & IBoundingBoxProvider> extends SafeBlockEntityRenderer<T> {
        public final BlockEntityRendererProvider.Context context;

        public TestingRenderer(BlockEntityRendererProvider.Context context) {
            this.context = context;
        }

        @Override
        protected void renderSafe(T be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
//            if (be.getBlockState().getBlock() instanceof IBoundingBoxProvider p) {
//                VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.lines());
//
//                LevelRenderer.renderLineBox(ms, vertexconsumer, new AABB(be.getBlockPos()), 0.257F, 0.541F, 1, 0.8F);
//
//                BoundingBox relativeBound = p.getBoundingBox();
//                AABB bound = new AABB(relativeBound.minX(), relativeBound.minY(), relativeBound.minZ(), relativeBound.maxX() + 1, relativeBound.maxY() + 1, relativeBound.maxZ() + 1)
//                        .move(be.getBlockPos());
//                LevelRenderer.renderLineBox(ms, vertexconsumer, bound, 1, 0.364F, 0.423F, 1);
//            }

            BlockPos pos = be.getBlockPos();
            BoundingBox boundingBox = be.getBoundingBox();
            String string = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(be.getType()).toLanguageKey();

            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Outliner.getInstance().showAABB(Pair.of(string + ".controller", pos), new AABB(pos))
                        .colored(0xFF_ff5d6c);

                Outliner.getInstance().showAABB(Pair.of(string + ".bound", pos), from(boundingBox))
                        .colored(0xFF_3589FF);
            });
        }
    }

    public static AABB from(BoundingBox box){
        return new AABB(box.minX(), box.minY(), box.minZ(), box.maxX() + 1, box.maxY() + 1, box.maxZ() + 1);
    }

    public static BoundingBox rotateBox(BoundingBox box, Direction direction) {
        return switch (direction) {
            case SOUTH -> box; // 南方向不变
            case WEST -> new BoundingBox( // 旋转90度（左转）
                    -box.maxZ(),  // 原最大Z取负 -> 新最小X
                    box.minY(),
                    box.minX(),   // 原最小X -> 新最小Z
                    -box.minZ(),  // 原最小Z取负 -> 新最大X
                    box.maxY(),
                    box.maxX()    // 原最大X -> 新最大Z
            );
            case NORTH -> new BoundingBox( // 旋转180度
                    -box.maxX(),  // 原最大X取负 -> 新最小X
                    box.minY(),
                    -box.maxZ(),  // 原最大Z取负 -> 新最小Z
                    -box.minX(),  // 原最小X取负 -> 新最大X
                    box.maxY(),
                    -box.minZ()   // 原最小Z取负 -> 新最大Z
            );
            case EAST -> new BoundingBox( // 旋转270度（右转）
                    box.minZ(),   // 原最小Z -> 新最小X
                    box.minY(),
                    -box.maxX(),  // 原最大X取负 -> 新最小Z
                    box.maxZ(),   // 原最大Z -> 新最大X
                    box.maxY(),
                    -box.minX()   // 原最小X取负 -> 新最大Z
            );
            default -> box; // 上下方向保持不变
        };
    }

    public static BlockPos rerotatePos(BlockPos pos, Direction direction) {
        return switch (direction) {
            case SOUTH -> pos; // 南方向不变
            case WEST -> new BlockPos( // 反向旋转90度（抵消左转）
                    pos.getZ(),  // 原Z -> 新X
                    pos.getY(),
                    -pos.getX() // 原X取负 -> 新Z
            );
            case NORTH -> new BlockPos( // 反向旋转180度
                    -pos.getX(), // 原X取负 -> 新X
                    pos.getY(),
                    -pos.getZ()  // 原Z取负 -> 新Z
            );
            case EAST -> new BlockPos( // 反向旋转270度（抵消右转）
                    -pos.getZ(), // 原Z取负 -> 新X
                    pos.getY(),
                    pos.getX()   // 原X -> 新Z
            );
            default -> pos; // 上下方向保持不变
        };
    }
}