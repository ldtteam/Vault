package com.ldtteam.vault.common.proxy;

import com.ldtteam.vault.api.IVaultAPI;
import com.ldtteam.vault.api.VaultAPI;
import com.ldtteam.vault.api.common.proxy.IVaultProxy;
import com.ldtteam.vault.api.permission.VaultPermissionHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.server.permission.PermissionAPI;
import org.jetbrains.annotations.NotNull;

public class CommonVaultProxy implements IVaultProxy
{

    @Override
    public void setupAPI()
    {
        IVaultAPI.Holder.setApi(new VaultAPI());
    }

    @Override
    public void preInit(@NotNull final FMLPreInitializationEvent event)
    {
        PermissionAPI.setPermissionHandler(new VaultPermissionHandler());
    }
}
