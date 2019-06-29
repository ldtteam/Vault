package com.ldtteam.vault.common.context;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.server.permission.context.Context;
import net.minecraftforge.server.permission.context.ContextKey;
import net.minecraftforge.server.permission.context.ContextKeys;

import javax.annotation.Nullable;

public class TileEntityContext extends Context
{

    private final TileEntity tileEntity;

    public TileEntityContext(final TileEntity tileEntity) {this.tileEntity = tileEntity;}

    @Nullable
    @Override
    public World getWorld()
    {
        return tileEntity.getWorld();
    }

    @Nullable
    @Override
    public <T> T get(final ContextKey<T> key)
    {
        if (key == ContextKeys.POS)
            return (T) tileEntity.getPos();

        return super.get(key);
    }

    @Override
    protected boolean covers(final ContextKey<?> key)
    {
        return key == ContextKeys.POS || super.covers(key);
    }
}
