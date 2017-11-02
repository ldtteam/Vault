package com.minecolonies.vault.api.region;

import com.minecolonies.vault.api.inheritance.ISaveableInheritanceTree;

/**
 * This interface describes a {@link IRegion region}.
 * A {@link IRegion region} is a part of the Minecraft universe.
 *
 * A single {@link IRegion region} can be divided into multiple children each with their own properties and characteristics.
 * These divisions of {@link IRegion regions} into smaller children is implemented into an inheritance tree.
 */
public interface IRegion extends ISaveableInheritanceTree<IRegion>
{

}
