package com.minecolonies.vault.api.region;

import com.minecolonies.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import com.minecolonies.vault.api.location.ILocation;
import com.minecolonies.vault.api.permission.IPermission;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This interface describes a {@link IRegion region}.
 * A {@link IRegion region} is a part of the Minecraft universe.
 *
 * A single {@link IRegion region} can be divided into multiple children each with their own properties and characteristics.
 * These divisions of {@link IRegion regions} into smaller children is implemented into an inheritance tree.
 */
public interface IRegion extends ISaveableDataHoldingInheritanceTree<IRegion, IPermission, NBTTagCompound>
{

    /**
     * Method used to check if a given {@link ILocation location} is contained in the region.
     * @param location The location to check for.
     * @return True when in region, false when not.
     */
    boolean isIn(@NotNull final ILocation location);

    /**
     * Method used to get the smallest possible child of this region that contains the {@link ILocation target}.
     * @param target The {@link ILocation target} to look for.
     * @return The smallest possible child of this region that contains the target. Null if not found.
     */
    @Nullable
    default IRegion getSmallestChild(@NotNull final ILocation target){
        if (!isIn(target))
            return null;

        return getDeepestChild(r -> r.isIn(target));
    }

    @Override
    default NBTTagCompound serializeData(){
        return getData().serializeNBT();
    }

    @Override
    IPermission deserializeData(@NotNull final NBTTagCompound dataNbt);
}
