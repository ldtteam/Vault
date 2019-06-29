package com.ldtteam.vault.api;

import com.ldtteam.vault.api.event.IEventPermissionHandler;
import com.ldtteam.vault.api.permission.IVaultPermissionHandler;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public interface IVaultAPI
{
    Logger getLogger();

    IEventPermissionHandler getEventHandler();

    IVaultPermissionHandler getVaultPermissionHandler();

    /**
     * Holds the API instance so that it can be accessed from the API itself and by other without having to use IMC.
     * Available after PreInit.
     */
    class Holder
    {
        private static IVaultAPI api;

        public static void setApi(@NotNull final IVaultAPI api) throws IllegalStateException
        {
            if (Holder.api != null)
                throw new IllegalStateException("Api already set.");

            Holder.api = api;
        }

        @NotNull
        public static IVaultAPI getApi()
        {
            return api;
        }
    }
}
