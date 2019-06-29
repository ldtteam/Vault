package com.ldtteam.vault.common.context;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.server.permission.context.Context;
import net.minecraftforge.server.permission.context.ContextKey;
import net.minecraftforge.server.permission.context.ContextKeys;

import javax.annotation.Nullable;

public class EntityContext extends Context
{
    private final Entity entity;

    public EntityContext(final Entity entity) {this.entity = entity;}

    @Nullable
    @Override
    public World getWorld()
    {
        return entity.getEntityWorld();
    }

    @Nullable
    @Override
    public <T> T get(final ContextKey<T> key)
    {
        if(key == ContextKeys.POS)
            return (T) entity.getPosition();

        return super.get(key);
    }

    @Override
    protected boolean covers(final ContextKey<?> key)
    {
        return key == ContextKeys.POS || super.covers(key);
    }
}
