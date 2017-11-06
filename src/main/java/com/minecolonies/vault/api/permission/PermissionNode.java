package com.minecolonies.vault.api.permission;

import com.google.common.collect.ImmutableSet;
import com.minecolonies.vault.api.inheritance.ISaveableInheritanceTree;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class PermissionNode implements IPermissionNode
{

    private IPermissionNode parent;
    private Set<IPermissionNode> children = new HashSet<>();
    private IPermissionNodeData permissionNodeData = new PermissionNodeData();

    @Override
    public IPermissionNodeData getNewDataInstance()
    {
        return new PermissionNodeData();
    }

    @Override
    public NBTTagCompound serializeData()
    {
        return getData().serializeNBT();
    }

    @Override
    public void setData(@Nullable final IPermissionNodeData data)
    {
        this.permissionNodeData = data;
    }

    @Nullable
    @Override
    public IPermissionNodeData getData()
    {
        return permissionNodeData;
    }

    @Override
    public ISaveableInheritanceTree<IPermissionNode> getNewInstance()
    {
        return new PermissionNode();
    }

    @Nullable
    @Override
    public IPermissionNode getParent()
    {
        return parent;
    }

    @Override
    public IPermissionNode setParent(@Nullable final IPermissionNode parent)
    {
        if (this.parent != null)
            parent.removeChild(this);

        this.parent = parent;

        if (this.parent != null)
            parent.addChild(this);

        return this;
    }

    @Override
    public ImmutableSet<IPermissionNode> getChildren()
    {
        return ImmutableSet.copyOf(children);
    }

    @Override
    public IPermissionNode addChild(@NotNull final IPermissionNode child) throws IllegalArgumentException
    {
        if (this.children.contains(child))
            throw new IllegalArgumentException("The child is already registered to this element");

        this.children.add(child);

        return this;
    }

    @Override
    public IPermissionNode removeChild(@NotNull final IPermissionNode child) throws IllegalArgumentException
    {
        if (!this.children.contains(child))
            throw new IllegalArgumentException("The child is not registered to this element");

        this.children.remove(child);

        return this;
    }

    @Override
    public IPermissionNode cloneAsEmptyRoot()
    {
        IPermissionNode node = new PermissionNode();
        node.setData(node.deserializeData(this.serializeData()));
        return node;
    }
}
