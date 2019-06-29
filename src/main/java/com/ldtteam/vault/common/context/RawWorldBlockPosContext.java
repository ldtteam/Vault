package com.ldtteam.vault.common.context;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.server.permission.context.Context;
import net.minecraftforge.server.permission.context.ContextKey;
import net.minecraftforge.server.permission.context.ContextKeys;

import javax.annotation.Nullable;

public class RawWorldBlockPosContext extends Context
{
    private final World world;
    private final BlockPos blockPos;

    public RawWorldBlockPosContext(final World world, final BlockPos blockPos) {
        this.world = world;
        this.blockPos = blockPos;
    }

    @Nullable
    @Override
    public World getWorld()
    {
        return world;
    }

    @Nullable
    @Override
    public <T> T get(final ContextKey<T> key)
    {
        if (key == ContextKeys.POS)
            return (T) blockPos;

        return super.get(key);
    }

    @Override
    protected boolean covers(final ContextKey<?> key)
    {
        return key == ContextKeys.POS || super.covers(key);
    }
}
