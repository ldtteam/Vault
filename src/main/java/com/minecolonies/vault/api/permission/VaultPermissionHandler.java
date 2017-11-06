package com.minecolonies.vault.api.permission;

import com.minecolonies.vault.api.region.IRegion;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.context.IContext;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * ------------ Class not Documented ------------
 */
public class VaultPermissionHandler implements IVaultPermissionHandler
{
    private IRegion defaultRootRegion;

    public VaultPermissionHandler()
    {

    }

    private void setupDefaultRootRegion() {

    }

    @Override
    public void registerNode(final String node, final DefaultPermissionLevel level, final String desc)
    {

    }

    @Override
    public Collection<String> getRegisteredNodes()
    {
        return null;
    }

    @Override
    public boolean hasPermission(final GameProfile profile, final String node, @Nullable final IContext context)
    {
        return false;
    }

    @Override
    public String getNodeDescription(final String node)
    {
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return null;
    }

    @Override
    public void deserializeNBT(final NBTTagCompound nbt)
    {

    }
}
