package com.ldtteam.vault.api.permission;

import com.ldtteam.vault.api.inheritance.ISaveableInheritanceTree;
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

    /**
     * Method used to get the entire name of the permission node.
     * @return the entire name of the permission node.
     */
    @NotNull
    default String getName()
    {
        if (isRoot())
            return getKey();

        return String.format("%s.%s", getParent().getName(), getKey());
    }
}
