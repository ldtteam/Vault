package com.minecolonies.vault.api.grouping;

import com.minecolonies.vault.api.inheritance.ISaveableDataHoldingInheritanceTree;
import com.minecolonies.vault.api.permission.IPermissionNode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface IGroup<G extends IGroup<G, P>, P extends IPermissionNode<P>> extends ISaveableDataHoldingInheritanceTree<G, P, NBTTagCompound>
{

    /**
     * Returns the groups key.
     * @return The key of the group.
     */
    @NotNull
    String getKey();

    /**
     * Method to get the description of the group.
     * @return The description of the group.
     */
    @NotNull
    String getDescription();

    default boolean hasDescription()
    {
        return getDescription() != null && !getDescription().isEmpty();
    }

    /**
     * Method to get the tag shown in front of the player name if set.
     * @return The tag of this group
     */
    @NotNull
    String getTag();

    default boolean hasTag()
    {
        return getTag() != null && !getTag().isEmpty();
    }

    /**
     * Method to get the players in this group.
     * @return The players in this group.
     */
    @NotNull
    Set<UUID> getPlayers();

    /**
     * Method to get the default vanilla permission level.
     * For custom groups this is always null. For build-in groups that represent a vanilla operator level, this returns the value indicating the vanilla group.
     *
     * @return The default vanilla permission level that this is equal too.
     */
    @Nullable
    DefaultPermissionLevel getVanillaPermissionLevel();
}
