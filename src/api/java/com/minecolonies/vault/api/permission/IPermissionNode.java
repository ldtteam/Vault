package com.minecolonies.vault.api.permission;

import com.minecolonies.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import com.minecolonies.vault.api.inheritance.ISaveableInheritanceTree;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

public interface IPermissionNode extends ISaveableDataHoldingInheritanceTree<IPermissionNode, IPermissionNodeData, NBTTagCompound>
{

    @Override
    default IPermissionNodeData deserializeData(@NotNull final NBTTagCompound dataNbt)
    {
        IPermissionNodeData newInstance = getNewDataInstance();

        newInstance.deserializeNBT(dataNbt);

        return newInstance;
    }

    /**
     * Method used to get a new instance of the {@link IPermissionNodeData data}
     * @return Thew new standard instance of the {@link IPermissionNodeData data}
     */
    IPermissionNodeData getNewDataInstance();
}
