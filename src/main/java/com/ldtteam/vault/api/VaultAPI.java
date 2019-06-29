package com.ldtteam.vault.api;

import com.ldtteam.vault.api.event.EventPermissionHandler;
import com.ldtteam.vault.api.event.IEventPermissionHandler;
import com.ldtteam.vault.api.permission.IVaultPermissionHandler;
import com.ldtteam.vault.api.permission.VaultPermissionHandler;
import com.ldtteam.vault.api.utils.LogUtils;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.function.Consumer;

public class VaultAPI implements IVaultAPI
{

    private static final Logger apiLogger = LogUtils.constructLoggerForClass(VaultAPI.class);

    @Override
    public Logger getLogger()
    {
        return apiLogger;
    }

    @Override
    public IEventPermissionHandler getEventHandler()
    {
        return EventPermissionHandler.getInstance();
    }

    @Override
    public IVaultPermissionHandler getVaultPermissionHandler()
    {
        return VaultPermissionHandler.getInstance();
    }
}
