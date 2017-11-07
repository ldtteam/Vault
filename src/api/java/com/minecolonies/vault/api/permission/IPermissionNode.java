package com.minecolonies.vault.api.permission;

import com.minecolonies.vault.api.inheritance.ISaveableInheritanceTree;
import org.jetbrains.annotations.NotNull;

public interface IPermissionNode<N extends IPermissionNode<N>> extends ISaveableInheritanceTree<N>
{
    /**
     * Method to get the key of the permission node.
     * @return The key of the permission node.
     */
    @NotNull
    String getKey();

    /**
     * Method to get the description of the permission node.
     * @return The description of the permission node.
     */
    @NotNull
    String getDesc();

    /**
     * Method to get the type of the permission node.
     * @return The type of the permission node.
     */
    @NotNull
    PermissionType getType();
}
