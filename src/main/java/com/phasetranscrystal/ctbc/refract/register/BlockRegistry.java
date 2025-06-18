package com.phasetranscrystal.ctbc.refract.register;

import com.phasetranscrystal.ctbc.refract.CtBCRefract;
import com.phasetranscrystal.ctbc.refract.obj.AssemblyMac;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, CtBCRefract.MODID);

    public static final RegistryObject<AssemblyMac> ASSEMBLY_MAC =
            REGISTER.register("assembly_mac",AssemblyMac::new);

}
