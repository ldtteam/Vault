package com.minecolonies.vault.api.grouping;

import com.minecolonies.vault.api.inheritance.VaultSaveableDataHoldingInheritanceTreeElement;
import com.minecolonies.vault.api.permission.VaultPermissionNode;
import com.minecolonies.vault.api.permission.VaultPermissionNodeData;
import net.minecraft.nbt.NBTTagCompound;

/**
 * ------------ Class not Documented ------------
 */
public class VaultGroup extends VaultSaveableDataHoldingInheritanceTreeElement<VaultGroup, VaultPermissionNode, NBTTagCompound> implements IGroup<VaultGroup, VaultPermissionNode, VaultPermissionNodeData, NBTTagCompound>
{
    @Override
    public VaultPermissionNode getNewDataInstance()
    {
        return new VaultPermissionNode();
    }

    @Override
    public VaultGroup getNewInstance()
    {
        return new VaultGroup();
    }
}
