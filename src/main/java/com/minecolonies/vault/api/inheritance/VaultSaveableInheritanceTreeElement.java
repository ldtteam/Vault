package com.minecolonies.vault.api.inheritance;

import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract implementation of the {@link ISaveableInheritanceTree} interface.
 * Takes care of all the tree-type properties, like parents and children.
 */
public abstract class VaultSaveableInheritanceTreeElement<E extends VaultSaveableInheritanceTreeElement<E>>
  implements ISaveableInheritanceTree<E>
{
    private E parent;
    private Set<E> children = new HashSet<>();

    @Nullable
    @Override
    public E getParent()
    {
        return parent;
    }

    @Override
    public E setParent(@Nullable final E parent)
    {
        if (this.parent != null)
            parent.removeChild((E) this);

        this.parent = parent;

        if (this.parent != null)
            parent.addChild((E) this);

        return (E) this;
    }

    @Override
    public ImmutableSet<E> getChildren()
    {
        return ImmutableSet.copyOf(children);
    }

    @Override
    public E addChild(@NotNull final E child) throws IllegalArgumentException
    {
        if (this.children.contains(child))
            throw new IllegalArgumentException("The child is already registered to this element");

        this.children.add(child);

        return (E) this;
    }

    @Override
    public E removeChild(@NotNull final E child) throws IllegalArgumentException
    {
        if (!this.children.contains(child))
            throw new IllegalArgumentException("The child is not registered to this element");

        this.children.remove(child);

        return (E) this;
    }

    @Override
    public E cloneAsEmptyRoot()
    {
        return getNewInstance();
    }
}
