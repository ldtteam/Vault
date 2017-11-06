package com.minecolonies.vault.api.permission;

import com.minecolonies.vault.api.inheritance.VaultSaveableDataHoldingInheritanceTreeElement;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A single permission node in the permission tree.
 */
public class VaultPermissionNode extends VaultSaveableDataHoldingInheritanceTreeElement<VaultPermissionNode, VaultPermissionNodeData, NBTTagCompound> implements IPermissionNode<VaultPermissionNode, VaultPermissionNodeData, NBTTagCompound>
{
    @Override
    public VaultPermissionNode getNewInstance()
    {
        return new VaultPermissionNode();
    }

    @Override
    public VaultPermissionNodeData getNewDataInstance()
    {
        return new VaultPermissionNodeData();
    }
}
