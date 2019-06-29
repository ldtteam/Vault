package com.ldtteam.vault.api.region;

import com.ldtteam.vault.api.grouping.IGroup;
import com.ldtteam.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import com.ldtteam.vault.api.location.ILocation;
import com.ldtteam.vault.api.permission.IPermissionNode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This interface describes a {@link IRegion region}.
 * A {@link IRegion region} is a part of the Minecraft universe.
 *
 * A single {@link IRegion region} can be divided into multiple children each with their own properties and characteristics.
 * These divisions of {@link IRegion regions} into smaller children is implemented into an inheritance tree.
 */
public interface IRegion<R extends IRegion<R, G, P>, G extends IGroup<G, P>, P extends IPermissionNode<P>> extends ISaveableDataHoldingInheritanceTree<R, G, NBTTagCompound>
{

    /**
     * Method used to check if a given {@link ILocation location} is contained in the region.
     * @param location The location to check for.
     * @return True when in region, false when not.
     */
    boolean isIn(@NotNull final ILocation location);

    /**
     * Method used to get the smallest possible child of this region that contains the {@link BlockPos target}.
     * @param target The {@link BlockPos target} to look for.
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
     * @return The dimension Id this world is in, null if it is serverwide.
     */
    Integer getDimensionId();

    /**
     * Method used to check is this region is server wide.
     * @return True when server wide, false when not.
     */
    default boolean isServerWide()
    {
        return getDimensionId() == null;
    }

    /**
     * Returns the BB of the region.
     * @return The bounding box of the reqion, or null when it is worldwide.
     */
    @Nullable
    AxisAlignedBB getRegion();

    /**
     * Method used to check if this region is world wide.
     * Is always true when this is a serverwide region ({@link #isServerWide()} returns true).
     *
     * @return True when world wide, false when not.
     */
    default boolean isWorldWide()
    {
        return isServerWide() || getRegion() == null;
    }
}
