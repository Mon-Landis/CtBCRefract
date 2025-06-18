package com.phasetranscrystal.ctbc.refract.register;

import com.phasetranscrystal.ctbc.refract.CtBCRefract;
import com.phasetranscrystal.ctbc.refract.obj.AssemblyMac;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CtBCRefract.MODID);

    public static final RegistryObject<BlockEntityType<AssemblyMac.Entity>> ASSEMBLY_MAC = REGISTER.register("assembly_mac",() -> BlockEntityType.Builder.of(AssemblyMac.Entity::new,BlockRegistry.ASSEMBLY_MAC.get()).build(null));

}
