package com.minecolonies.vault.api;

import com.minecolonies.vault.api.utils.LogUtils;
import org.apache.logging.log4j.Logger;

public class VaultAPI implements IVaultAPI
{

    private static final Logger apiLogger = LogUtils.constructLoggerForClass(VaultAPI.class);

    @Override
    public Logger getLogger()
    {
        return apiLogger;
    }
}
