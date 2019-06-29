package com.ldtteam.vault.api.permission;

import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.server.permission.IPermissionHandler;

/**
 * The central Vault permission handler.
 */
public interface IVaultPermissionHandler extends IPermissionHandler, INBTSerializable<NBTTagList>
{

}
