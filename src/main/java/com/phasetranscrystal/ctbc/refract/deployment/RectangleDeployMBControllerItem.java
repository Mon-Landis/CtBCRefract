package com.phasetranscrystal.ctbc.refract.deployment;

import com.phasetranscrystal.ctbc.refract.helper.BoundingBoxHelper;
import com.phasetranscrystal.ctbc.refract.obj.AssemblyMac;
import net.createmod.catnip.data.Pair;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

public class RectangleDeployMBControllerItem extends BlockItem {
    public RectangleDeployMBControllerItem(AssemblyMac pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext ctx) {
        InteractionResult result = super.place(ctx);

        if (result == InteractionResult.FAIL && ctx.getLevel().isClientSide() && ctx.getPlayer() instanceof LocalPlayer localPlayer) {
            BlockPos clickedPos = ctx.getClickedPos();
            AABB region = BoundingBoxHelper.from(((AssemblyMac) getBlock())
                    .getRegion(ctx.getHorizontalDirection().getOpposite())
                    .moved(clickedPos.getX(), clickedPos.getY(), clickedPos.getZ())
            );
            String name = ForgeRegistries.BLOCKS.getKey(getBlock()).toLanguageKey();

            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Outliner.getInstance().showAABB(Pair.of(name, clickedPos), region)
                        .colored(0xFF_ff5d6c);
                localPlayer.displayClientMessage(
                        Component.translatable("ctbc.message_meta.not_enough_space",
                                I18n.get("block." + name),
                                region
                                ),
                        true);
            });
        }

        return result;
    }

}
