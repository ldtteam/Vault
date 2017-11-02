package com.minecolonies.vault.api.inheritance;

import org.jetbrains.annotations.Nullable;

public interface IReadWriteDataHoldingInheritanceTreeElement<E extends IReadWriteDataHoldingInheritanceTreeElement<E, D>, D> extends IReadOnlyDataHoldingInheritanceTreeElement<E, D>
{

    /**
     * Method used to set the {@link D data} stored in this {@link IReadWriteDataHoldingInheritanceTreeElement<E, D> element}
     * @param data The data to be stored, null to clear.
     */
    void setData(@Nullable D data);

    /**
     * Convenience method to delete the {@link D data} stored.
     */
    default void clear()
    {
        setData(null);
    }
}
