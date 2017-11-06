package com.minecolonies.vault.api.inheritance;

import com.google.common.collect.ImmutableSet;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * ------------ Class not Documented ------------
 */
public abstract class VaultSaveableDataHoldingInheritanceTreeElement<E extends VaultSaveableDataHoldingInheritanceTreeElement<E, D, N> , D extends INBTSerializable<N>, N extends NBTBase> implements ISaveableDataHoldingInheritanceTree<E, D, N>
{

    private E parent;
    private Set<E> children           = new HashSet<>();
    private D       D;

    @Override
    public void setData(@Nullable final D data)
    {
        this.D = data;
    }

    @Nullable
    @Override
    public D getData()
    {
        return D;
    }

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
        E node = getNewInstance();
        node.setData(node.deserializeData(this.serializeData()));
        return node;
    }
}
