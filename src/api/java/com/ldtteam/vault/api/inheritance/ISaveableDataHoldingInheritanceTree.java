package com.ldtteam.vault.api.inheritance;

import com.ldtteam.vault.api.utils.NBTUtils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.vault.api.constants.ModNBTConstants.Trees.CONST_NBT_CHILDREN;
import static com.ldtteam.vault.api.constants.ModNBTConstants.Trees.CONST_NBT_DATA;
import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public interface ISaveableDataHoldingInheritanceTree<E extends ISaveableDataHoldingInheritanceTree<E, D, N>, D extends INBTSerializable<N>, N extends NBTBase> extends ISaveableInheritanceTree<E>, IReadWriteDataHoldingInheritanceTreeElement<E, D>
{

    @Override
    default NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();

        NBTTagList childrenList = getChildren()
                                    .stream()
                                    .map(e -> (ISaveableInheritanceTree<E>) e)
                                    .map(ISaveableInheritanceTree::serializeNBT)
                                    .collect(NBTUtils.toNBTTagList());

        compound.setTag(CONST_NBT_CHILDREN, childrenList);
        compound.setTag(CONST_NBT_DATA, serializeData());

        return compound;
    }

    default N serializeData()
    {
        return getData().serializeNBT();
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

        N dataNbt = (N) nbt.getTag(CONST_NBT_DATA);
        D data = deserializeData(dataNbt);

        setData(data);
    }

    default D deserializeData(@NotNull final N dataNbt)
    {
        D data = getNewDataInstance();
        data.deserializeNBT(dataNbt);
        return data;
    }

    /**
     * Method to get a new clean instance of the data stored in this tree.
     * @return The clean instance of the data stored in this tree.
     */
    D getNewDataInstance();

}
