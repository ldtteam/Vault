package com.ldtteam.vault.api.inheritance;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract implementation of the {@link ISaveableDataHoldingInheritanceTree} interface.
 */
public abstract class VaultSaveableDataHoldingInheritanceTreeElement<E extends VaultSaveableDataHoldingInheritanceTreeElement<E, D, N> , D extends INBTSerializable<N>, N extends NBTBase>
  extends
  VaultSaveableInheritanceTreeElement<E> implements ISaveableDataHoldingInheritanceTree<E, D, N>
{

    private D       D;

    public VaultSaveableDataHoldingInheritanceTreeElement()
    {
        setData(getNewDataInstance());
    }

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

    @Override
    public E cloneAsEmptyRoot()
    {
        E data = getNewInstance();
        data.setData(this.deserializeData(this.serializeData()));
        return data;
    }
}
