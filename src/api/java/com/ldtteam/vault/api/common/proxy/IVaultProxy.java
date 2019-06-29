package com.ldtteam.vault.api.common.proxy;

import net.minecraftforge.fml.common.event.*;
import org.jetbrains.annotations.NotNull;

public interface IVaultProxy
{
    /**
     * Method called during the pre-init phase of the engine configuration.
     * @param event The {@link FMLPreInitializationEvent event} that is fired during engine configuration
     */
    default void preInit(@NotNull final FMLPreInitializationEvent event) {}


    /**
     * Method called during the init phase of the engine configuration.
     * @param event The {@link FMLInitializationEvent event} that is fired during engine configuration
     */
    default void init(final FMLInitializationEvent event) {}


    /**
     * Method called during the post-init phase of the engine configuration.
     * @param event The {@link FMLPostInitializationEvent event} that is fired during engine configuration
     */
    default void postInit(final FMLPostInitializationEvent event) {}


    /**
     * Method called during the pre-start phase of the engine configuration.
     * @param event The {@link FMLServerStartingEvent event} that is fired during engine configuration
     */
    default void serverLoad(final FMLServerStartingEvent event) {}

    /**
     * Method called during the start-start phase of the engine configuration.
     * @param event The {@link FMLServerStartedEvent event} that is fired during engine configuration
     */
    default void serverLoaded(final FMLServerStartedEvent event) {}

    /**
     * Method called to initialize the networking system.
     */
    default void setupNetwork() {}

    /**
     * Method called to initialize the API.
     */
    default void setupAPI() {}
}
