package com.minecolonies.vault.api.region;

import com.minecolonies.vault.api.grouping.VaultGroup;
import com.minecolonies.vault.api.inheritance.VaultSaveableDataHoldingInheritanceTreeElement;
import com.minecolonies.vault.api.location.ILocation;
import com.minecolonies.vault.api.permission.VaultPermissionNode;
import com.minecolonies.vault.api.permission.VaultPermissionNodeData;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ------------ Class not Documented ------------
 */
public class VaultServerRegion extends VaultSaveableDataHoldingInheritanceTreeElement<VaultServerRegion, VaultGroup, NBTTagCompound> implements IRegion<VaultServerRegion, VaultGroup, VaultPermissionNode, VaultPermissionNodeData, NBTTagCompound>
{

    @Override
    public boolean isIn(@NotNull final ILocation location)
    {
        return true;
    }

    @Override
    public VaultGroup getNewDataInstance()
    {
        return new VaultGroup();
    }

    @Override
    public VaultServerRegion getNewInstance()
    {
        return new VaultServerRegion();
    }

    @Override
    public VaultServerRegion setParent(@Nullable final VaultServerRegion parent)
    {
        throw new IllegalStateException("VaultServerRegion, is the serverwide location unknown permission handler. It cannot have any parent.");
    }

    @Override
    public VaultServerRegion addChild(@NotNull final VaultServerRegion child) throws IllegalArgumentException
    {
        throw new IllegalStateException("VaultServerRegion, is the serverwide location unknown permission handler. It cannot have any children.");
    }

    @Override
    public VaultServerRegion removeChild(@NotNull final VaultServerRegion child) throws IllegalArgumentException
    {
        throw new IllegalStateException("VaultServerRegion, is the serverwide location unknown permission handler. It cannot have any children.");
    }
}
