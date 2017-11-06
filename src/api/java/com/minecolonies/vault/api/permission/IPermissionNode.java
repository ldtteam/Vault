package com.minecolonies.vault.api.permission;

import com.minecolonies.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import net.minecraft.nbt.NBTBase;

public interface IPermissionNode<N extends IPermissionNode<N, D, T>, D extends IPermissionNodeData<T>, T extends NBTBase> extends ISaveableDataHoldingInheritanceTree<N, D, T>
{
}
