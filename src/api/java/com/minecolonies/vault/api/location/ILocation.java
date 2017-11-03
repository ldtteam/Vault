package com.minecolonies.vault.api.location;

import net.minecraft.util.math.BlockPos;

/**
 * Defines a place in the Minecraft universe.
 */
public interface ILocation
{

    /**
     * Method to get the ID of the dimension this location is in.
     * @return The if of the dimension.
     */
    int getDimensionId();

    /**
     * Method to get the position inside the dimension.
     * @return The position inside the dimension.
     */
    BlockPos getPosition();
}
