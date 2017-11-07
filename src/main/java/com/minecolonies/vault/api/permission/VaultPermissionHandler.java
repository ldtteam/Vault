package com.minecolonies.vault.api.permission;

import com.minecolonies.vault.api.grouping.VaultGroup;
import com.minecolonies.vault.api.region.VaultServerRegion;
import com.minecolonies.vault.api.utils.LogUtils;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.context.IContext;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * ------------ Class not Documented ------------
 */
public class VaultPermissionHandler implements IVaultPermissionHandler
{

    private final static Logger logger = LogUtils.constructLoggerForClass(VaultPermissionHandler.class);

    private final VaultServerRegion defaults = new VaultServerRegion();

    public VaultPermissionHandler()
    {

    }

    private void setupDefaultRootRegion() {

    }

    @Override
    public void registerNode(final String node, final DefaultPermissionLevel level, final String desc)
    {
        logger.info("Registering new permission node: " + node + " with default level: " + level + " and description: " + desc);
        final String[] nodeElements = node.split(".");

        registerNodeToDefaults(nodeElements, level, desc);
    }

    private void registerNodeToDefaults(final String[] nodeElements, final DefaultPermissionLevel level, final String desc)
    {
        VaultGroup defaultRootGroup = defaults.getData();
        VaultGroup matchingDefaultLevelGroup = defaultRootGroup.getDeepestChild(c -> c.getVanillaPermissionLevel() != null && c.getVanillaPermissionLevel().equals(level));

        VaultPermissionNode currentWorkingNode = matchingDefaultLevelGroup.getData();

        //Find the deepest node along the elements tree.
        int index = 0;
        for (int i = 0;index < nodeElements.length; index++)
        {
            String key = nodeElements[index];
            if (currentWorkingNode == null)
                break;

            currentWorkingNode = currentWorkingNode.getChildren().stream().filter(c -> c.getKey().equalsIgnoreCase(key)).findFirst().orElse(null);
        }

        if (currentWorkingNode != null)
        {
            logger.warn("Can not override already existing permission node on this default level!");
            return;
        }

        for (int i = 0; index < nodeElements.length; index++)
        {
            String key = nodeElements[index];

            final VaultPermissionNode newNode = new VaultPermissionNode(key, desc, (index == nodeElements.length - 1) ? PermissionType.ACCEPTING : PermissionType.DONOTCARE);
            newNode.setParent(currentWorkingNode);

            currentWorkingNode = newNode;
        }
    }

    @Override
    public Collection<String> getRegisteredNodes()
    {
        return null;
    }

    @Override
    public boolean hasPermission(final GameProfile profile, final String node, @Nullable final IContext context)
    {
        return false;
    }

    @Override
    public String getNodeDescription(final String node)
    {
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return null;
    }

    @Override
    public void deserializeNBT(final NBTTagCompound nbt)
    {

    }
}
