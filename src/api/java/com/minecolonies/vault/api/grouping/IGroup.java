package com.minecolonies.vault.api.grouping;

import com.minecolonies.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import com.minecolonies.vault.api.permission.IPermissionNode;
import com.minecolonies.vault.api.permission.IPermissionNodeData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IGroup<G extends IGroup<G, P, D, N>, P extends IPermissionNode<P, D, N>, D extends IPermissionNodeData<N>, N extends NBTBase> extends ISaveableDataHoldingInheritanceTree<G, P, NBTTagCompound>
{

}
