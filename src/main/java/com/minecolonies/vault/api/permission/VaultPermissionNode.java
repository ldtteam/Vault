package com.minecolonies.vault.api.permission;

import com.minecolonies.vault.api.inheritance.VaultSaveableInheritanceTreeElement;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

/**
 * A single permission node in the permission tree.
 */
public class VaultPermissionNode extends VaultSaveableInheritanceTreeElement<VaultPermissionNode> implements IPermissionNode<VaultPermissionNode>
{
    //NBTConstants
    private static final String CONST_NBT_KEY = "Key";
    private static final String CONST_NBT_DESC = "Desc";
    private static final String CONST_NBT_TYPE = "Type";

    private String key;
    private String desc;
    private PermissionType type;

    public VaultPermissionNode()
    {
        this("*", "Root permission level", PermissionType.DONOTCARE);
    }

    public VaultPermissionNode(final String key, final String desc, final PermissionType type)
    {
        this.key = key;
        this.desc = desc;
        this.type = type;
    }

    @Override
    public VaultPermissionNode getNewInstance()
    {
        return new VaultPermissionNode();
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = super.serializeNBT();
        compound.setString(CONST_NBT_KEY, key);
        compound.setString(CONST_NBT_DESC, desc);
        compound.setInteger(CONST_NBT_TYPE, type.ordinal());
        return compound;
    }

    @Override
    public void deserializeNBT(final NBTTagCompound nbt)
    {
        super.deserializeNBT(nbt);
        this.key = nbt.getString(CONST_NBT_KEY);
        this.desc = nbt.getString(CONST_NBT_DESC);
        this.type = PermissionType.values()[nbt.getInteger(CONST_NBT_TYPE)];
    }

    @Override
    @NotNull
public String getKey()
{
    return key;
}

    @Override
    @NotNull
    public String getDesc()
    {
        return desc;
    }

    @Override
    @NotNull
    public PermissionType getType()
    {
        return type;
    }
}
