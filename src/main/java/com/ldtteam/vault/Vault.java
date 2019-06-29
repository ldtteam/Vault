package com.ldtteam.vault;

import com.ldtteam.vault.api.common.proxy.IVaultProxy;
import com.ldtteam.vault.api.constants.ModConstants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.jetbrains.annotations.NotNull;

@Mod(modid = ModConstants.CONST_MOD_ID, version = ModConstants.CONST_MOD_VERSION,
acceptedMinecraftVersions = ModConstants.CONST_MINECRAFT_VERSION)
public class Vault
{

    /**
     * Forge created instance of the Mod.
     */
    @Mod.Instance(ModConstants.CONST_MOD_ID)
    private static  Vault         instance;

    /**
     * Access to the proxy associated with your current side. Variable updated
     * by forge.
     */
    @SidedProxy(clientSide = ModConstants.CONST_PROXY_CLIENT, serverSide = ModConstants.CONST_PROXY_COMMON)
    public static IVaultProxy proxy;

    @Mod.EventHandler
    public void preInit(@NotNull final FMLPreInitializationEvent event)
    {
        proxy.setupAPI();
        proxy.setupNetwork();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
        PermissionAPI.registerNode("Vault.Test", DefaultPermissionLevel.OP, "Test command for Vault");
    }

    @Mod.EventHandler
    public void serverLoad(final FMLServerStartingEvent event)
    {
        proxy.serverLoad(event);
    }

    @Mod.EventHandler
    public void onFMLServerStarted(final FMLServerStartedEvent event)
    {
        proxy.serverLoaded(event);
    }
}
