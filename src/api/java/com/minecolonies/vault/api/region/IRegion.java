package com.minecolonies.vault.api.region;

import com.minecolonies.vault.api.grouping.IGroup;
import com.minecolonies.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import com.minecolonies.vault.api.location.ILocation;
import com.minecolonies.vault.api.permission.IPermissionNode;
import com.minecolonies.vault.api.permission.IPermissionNodeData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This interface describes a {@link IRegion region}.
 * A {@link IRegion region} is a part of the Minecraft universe.
 *
 * A single {@link IRegion region} can be divided into multiple children each with their own properties and characteristics.
 * These divisions of {@link IRegion regions} into smaller children is implemented into an inheritance tree.
 */
public interface IRegion<R extends IRegion<R, G, P, D, N>, G extends IGroup<G, P, D, N>, P extends IPermissionNode<P, D, N>, D extends IPermissionNodeData<N>, N extends NBTBase> extends ISaveableDataHoldingInheritanceTree<R, G, NBTTagCompound>
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

    /**
     * Method to get the dimension Id of the world this Region is in.
     * @return The dimension Id this world is in.
     */
    int getDimensionId();

    /**
     *
     * @return
     */
    @Nullable
    AxisAlignedBB getRegion();
}
