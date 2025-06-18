package com.phasetranscrystal.ctbc.refract;

import com.phasetranscrystal.ctbc.refract.register.BlockEntityRegistry;
import com.phasetranscrystal.ctbc.refract.register.BlockRegistry;
import com.phasetranscrystal.ctbc.refract.register.ItemRegistry;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(CtBCRefract.MODID)
public class CtBCRefract {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "ctbc_refract";

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public CtBCRefract(FMLJavaModLoadingContext modLoadingContext) {
        IEventBus event = modLoadingContext.getModEventBus();
        TAB_REGISTER.register(event);

        BlockRegistry.REGISTER.register(event);
        ItemRegistry.REGISTER.register(event);
        BlockEntityRegistry.REGISTER.register(event);
    }

    public static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> CTBC_TAB =
            TAB_REGISTER.register("tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(AllItems.PRECISION_MECHANISM.get()))
                    .displayItems((params,output) -> {
                        output.accept(ItemRegistry.ASSEMBLY_MAC.get());
                    })
                    .build());


}
