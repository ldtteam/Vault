package com.ldtteam.vault.api.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

/**
 * ------------ Class not Documented ------------
 */
public class BlockPosUtils
{

    public static Vec3d toVector(@NotNull final BlockPos pos)
    {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }
}
