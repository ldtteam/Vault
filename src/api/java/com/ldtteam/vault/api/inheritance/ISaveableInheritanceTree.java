package com.ldtteam.vault.api.inheritance;

import com.ldtteam.vault.api.utils.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;

import static com.ldtteam.vault.api.constants.ModNBTConstants.Trees.CONST_NBT_CHILDREN;
import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public interface ISaveableInheritanceTree<E extends ISaveableInheritanceTree<E>> extends IInheritanceTreeElement<E>, INBTSerializable<NBTTagCompound>
{

    @Override
    default NBTTagCompound serializeNBT(){
        NBTTagCompound compound = new NBTTagCompound();

        NBTTagList childrenList = getChildren()
                                    .stream()
                                    .map(e -> (ISaveableInheritanceTree<E>) e)
                                    .map(ISaveableInheritanceTree::serializeNBT)
                                    .collect(NBTUtils.toNBTTagList());

        compound.setTag(CONST_NBT_CHILDREN, childrenList);

        return compound;
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt)
    {
        NBTTagList childrenList = nbt.getTagList(CONST_NBT_CHILDREN, TAG_COMPOUND);

        NBTUtils.streamCompound(childrenList).forEach(c -> {
            final ISaveableInheritanceTree<E> newInstance = getNewInstance();

            newInstance.deserializeNBT(c);
            newInstance.setParent((E) this);
        });
    }

    E getNewInstance();
}
