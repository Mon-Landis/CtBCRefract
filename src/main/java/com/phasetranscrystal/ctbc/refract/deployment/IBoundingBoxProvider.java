package com.phasetranscrystal.ctbc.refract.deployment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public interface IBoundingBoxProvider {
    BoundingBox getBoundingBox();
}
