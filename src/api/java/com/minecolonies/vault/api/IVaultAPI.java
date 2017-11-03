package com.minecolonies.vault.api;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public interface IVaultAPI
{
    Logger getLogger();

    /**
     * Holds the API instance so that it can be accessed from the API itself and by other without having to use IMC.
     * Available after PreInit.
     */
    class Holder
    {
        private static IVaultAPI api;

        public void setApi(@NotNull final IVaultAPI api) throws IllegalStateException
        {
            if (this.api != null)
                throw new IllegalStateException("Api already set.");

            this.api = api;
        }

        @NotNull
        public static IVaultAPI getApi()
        {
            return api;
        }
    }
}
