package com.ldtteam.vault.api.inheritance;

import org.jetbrains.annotations.Nullable;

public interface IReadOnlyDataHoldingInheritanceTreeElement<E extends IReadOnlyDataHoldingInheritanceTreeElement<E, D>, D> extends IInheritanceTreeElement<E>
{

    /**
     * Method used to read the {@link D data} stored in this {@link IReadOnlyDataHoldingInheritanceTreeElement<E, D> element}.
     * @return The data stored in this element, null is not set.
     */
    @Nullable
    D getData();

    /**
     * Method used to check if this {@link IReadOnlyDataHoldingInheritanceTreeElement<E, D> element} has {@link D data} stored.
     * @return True when this element has data, fase
     */
    default boolean hasData() {
        return getData() != null;
    }
}
