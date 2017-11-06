package com.minecolonies.vault.api.permission;

import net.minecraft.nbt.NBTTagCompound;

public class VaultPermissionNodeData implements IPermissionNodeData<NBTTagCompound>
{

    //NBTTags:
    private static final String CONST_NBT_KEY = "Key";
    private static final String CONST_NBT_DESC = "Desc";

    private String key;
    private String desc;

    public VaultPermissionNodeData()
    {
        this("*", "Root permission");
    }

    public VaultPermissionNodeData(final String key, final String desc) {
        this.key = key;
        this.desc = desc;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString(CONST_NBT_KEY, key);
        compound.setString(CONST_NBT_DESC, desc);

        return compound;
    }

    @Override
    public void deserializeNBT(final NBTTagCompound nbt)
    {
        key = nbt.getString(CONST_NBT_KEY);
        desc = nbt.getString(CONST_NBT_DESC);
    }

    public String getKey()
    {
        return key;
    }

    public String getDesc()
    {
        return desc;
    }
}
