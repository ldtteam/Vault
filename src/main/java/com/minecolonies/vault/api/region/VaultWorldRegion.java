package com.minecolonies.vault.api.region;

import com.minecolonies.vault.api.grouping.VaultGroup;
import com.minecolonies.vault.api.inheritance.VaultSaveableDataHoldingInheritanceTreeElement;
import com.minecolonies.vault.api.location.ILocation;
import com.minecolonies.vault.api.permission.VaultPermissionNode;
import com.minecolonies.vault.api.utils.BlockPosUtils;
import com.minecolonies.vault.api.utils.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A piece of the minecraft universe in a world.
 */
public class VaultWorldRegion extends VaultSaveableDataHoldingInheritanceTreeElement<VaultWorldRegion, VaultGroup, NBTTagCompound> implements IRegion<VaultWorldRegion, VaultGroup, VaultPermissionNode>
{

    //NBTTags
    private static final String CONST_NBT_REGION = "Region";
    private static final String CONST_NBT_DIM = "Dim";

    private Integer dimensionId;
    private AxisAlignedBB region;

    public VaultWorldRegion(final int dimensionId)
    {
        this.dimensionId = dimensionId;
    }

    private VaultWorldRegion(final int dimensionId, final AxisAlignedBB region)
    {
        this.dimensionId = dimensionId;
        this.region = region;
    }

    @Override
    public boolean isIn(@NotNull final ILocation location)
    {
        if (location.getDimensionId() == dimensionId)
        {
            if (region == null)
                return true;

            return region.isVecInside(BlockPosUtils.toVector(location.getPosition()));
        }

        return false;
    }

    @Override
    public VaultGroup getNewDataInstance()
    {
        return new VaultGroup();
    }

    @Override
    public VaultWorldRegion getNewInstance()
    {
        return new VaultWorldRegion(dimensionId);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = super.serializeNBT();

        if (region != null)
            compound.setTag(CONST_NBT_REGION, NBTUtils.writeBoundingBox(region));

        compound.setInteger(CONST_NBT_DIM, dimensionId);

        return compound;
    }

    @Override
    public void deserializeNBT(final NBTTagCompound nbt)
    {
        super.deserializeNBT(nbt);

        if (nbt.hasKey(CONST_NBT_REGION))
            region = NBTUtils.readBoundingBox(nbt.getTagList(CONST_NBT_REGION, Constants.NBT.TAG_DOUBLE));

        dimensionId = nbt.getInteger(CONST_NBT_DIM);
    }

    @Override
    public Integer getDimensionId()
    {
        return dimensionId;
    }

    @Override
    @Nullable
    public AxisAlignedBB getRegion()
    {
        return region;
    }
}
