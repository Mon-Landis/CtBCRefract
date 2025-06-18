package com.phasetranscrystal.ctbc.refract;

import com.phasetranscrystal.ctbc.refract.helper.BoundingBoxHelper;
import com.phasetranscrystal.ctbc.refract.register.BlockEntityRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = CtBCRefract.MODID)
public class ClientEventConsumer {
    @SubscribeEvent
    public static void bindingBERenderer(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(BlockEntityRegistry.ASSEMBLY_MAC.get(), BoundingBoxHelper.TestingRenderer::new);
        });
    }
}
