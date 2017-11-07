package com.minecolonies.vault.api.grouping;

import com.google.common.collect.Sets;
import com.minecolonies.vault.api.inheritance.VaultSaveableDataHoldingInheritanceTreeElement;
import com.minecolonies.vault.api.permission.VaultPermissionNode;
import com.minecolonies.vault.api.utils.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents a group of players in the game with a given set of rights.
 * Any player belonging to this group in the region also belongs the groups that function as parent in this region and in all regions that are parents of this groups region.
 */
public class VaultGroup extends VaultSaveableDataHoldingInheritanceTreeElement<VaultGroup, VaultPermissionNode, NBTTagCompound> implements IGroup<VaultGroup, VaultPermissionNode>
{

    //NBTTag
    private static final String CONST_NBT_KEY = "Key";
    private static final String CONST_NBT_DESC = "Desc";
    private static final String CONST_NBT_TAG = "Tag";
    private static final String CONST_NBT_PLAYERS = "Players";
    private static final String CONST_NBT_VANILLA = "Vanilla";

    private String    key;
    private String desc;
    private String tag;
    private Set<UUID> players;
    private DefaultPermissionLevel vanillaLevel;

    public VaultGroup() {
        this("*", "", "", DefaultPermissionLevel.NONE);
    }

    public VaultGroup(final String key, final String desc, final String tag, final DefaultPermissionLevel vanillaLevel, final UUID... players) {
        this(key, desc, tag, vanillaLevel, Sets.newHashSet(players));
    }

    public VaultGroup(final String key, final String desc, final String tag, final DefaultPermissionLevel vanillaLevel, final Set<UUID> players)
    {
        this.key = key;
        this.tag = tag;
        this.desc = desc;
        this.players = players;
        this.vanillaLevel = vanillaLevel;
    }

    @Override
    public VaultPermissionNode getNewDataInstance()
    {
        return new VaultPermissionNode();
    }

    @Override
    public VaultGroup getNewInstance()
    {
        return new VaultGroup();
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = super.serializeNBT();
        compound.setString(CONST_NBT_KEY, key);
        compound.setString(CONST_NBT_DESC, desc);
        compound.setString(CONST_NBT_TAG, tag);
        if (vanillaLevel != null)
            compound.setInteger(CONST_NBT_VANILLA, vanillaLevel.ordinal());
        compound.setTag(CONST_NBT_PLAYERS, players.stream().map(NBTUtil::createUUIDTag).collect(NBTUtils.toNBTTagList()));
        return compound;
    }

    @Override
    public void deserializeNBT(final NBTTagCompound nbt)
    {
        super.deserializeNBT(nbt);
        key = nbt.getString(CONST_NBT_KEY);
        desc = nbt.getString(CONST_NBT_DESC);
        tag = nbt.getString(CONST_NBT_TAG);
        if (nbt.hasKey(CONST_NBT_VANILLA))
            vanillaLevel = DefaultPermissionLevel.values()[nbt.getInteger(CONST_NBT_VANILLA)];
        else
            vanillaLevel = null;
        players = NBTUtils.streamCompound(nbt.getTagList(CONST_NBT_PLAYERS, Constants.NBT.TAG_COMPOUND)).map(NBTUtil::getUUIDFromTag).collect(Collectors.toSet());
    }

    @Override
    @NotNull
    public String getKey()
    {
        return key;
    }

    @Override
    @NotNull
    public String getDescription()
    {
        return desc;
    }

    @Override
    @NotNull
    public String getTag()
    {
        return tag;
    }

    @Override
    @NotNull
    public Set<UUID> getPlayers()
    {
        return players;
    }

    @Nullable
    @Override
    public DefaultPermissionLevel getVanillaPermissionLevel()
    {
        return vanillaLevel;
    }
}
