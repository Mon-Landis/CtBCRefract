package com.phasetranscrystal.ctbc.refract;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(CtBCRefract.MODID)
public class CtBCRefract {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "ctbc_refract";

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
