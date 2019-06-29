package com.ldtteam.vault.api.location;

import net.minecraft.util.math.BlockPos;

public class Location implements ILocation
{
    private final int dimensionId;
    private final BlockPos inDimensionPosition;

    public Location(final int dimensionId, final BlockPos inDimensionPosition) {
        this.dimensionId = dimensionId;
        this.inDimensionPosition = inDimensionPosition;
    }

    @Override
    public int getDimensionId()
    {
        return dimensionId;
    }

    @Override
    public BlockPos getInDimensionPosition()
    {
        return inDimensionPosition;
    }
}
