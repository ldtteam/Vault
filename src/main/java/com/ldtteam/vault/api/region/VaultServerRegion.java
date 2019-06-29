package com.ldtteam.vault.api.region;

import com.ldtteam.vault.api.grouping.VaultGroup;
import com.ldtteam.vault.api.inheritance.VaultSaveableDataHoldingInheritanceTreeElement;
import com.ldtteam.vault.api.location.ILocation;
import com.ldtteam.vault.api.permission.VaultPermissionNode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the default configuration on a server.
 * Also known as server wide configuration.
 */
public class VaultServerRegion extends VaultSaveableDataHoldingInheritanceTreeElement<VaultServerRegion, VaultGroup, NBTTagCompound> implements IRegion<VaultServerRegion, VaultGroup, VaultPermissionNode>
{

    public VaultServerRegion()
    {
    }

    @Override
    public boolean isIn(@NotNull final ILocation location)
    {
        return true;
    }

    @Override
    public Integer getDimensionId()
    {
        return null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getRegion()
    {
        return null;
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
