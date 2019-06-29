package com.ldtteam.vault.api.location;

import net.minecraft.util.math.BlockPos;

/**
 * Represents a single location in the game universe.
 * Combines a dimension id and a position inside this dimension.
 */
public interface ILocation
{

    int getDimensionId();

    BlockPos getInDimensionPosition();
}
