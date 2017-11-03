package com.minecolonies.vault.api.grouping;

import com.minecolonies.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import com.minecolonies.vault.api.permission.IPermissionNode;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

public interface IGrouping extends ISaveableDataHoldingInheritanceTree<IGrouping, IPermissionNode, NBTTagCompound>
{

    @Override
    default IPermissionNode deserializeData(@NotNull final NBTTagCompound dataNbt)
    {
        IPermissionNode newInstance = getNewPermissionNodeInstance();

        newInstance.deserializeData(dataNbt);

        return newInstance;
    }

    IPermissionNode getNewPermissionNodeInstance();
}
