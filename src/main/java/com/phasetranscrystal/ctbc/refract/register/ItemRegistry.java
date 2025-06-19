package com.phasetranscrystal.ctbc.refract.register;

import com.phasetranscrystal.ctbc.refract.CtBCRefract;
import com.phasetranscrystal.ctbc.refract.deployment.RectangleDeployMBControllerItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CtBCRefract.MODID);

    public static final RegistryObject<Item> ASSEMBLY_MAC = REGISTER.register("assembly_mac",() -> new RectangleDeployMBControllerItem(BlockRegistry.ASSEMBLY_MAC.get(), new Item.Properties().stacksTo(1)));
}
