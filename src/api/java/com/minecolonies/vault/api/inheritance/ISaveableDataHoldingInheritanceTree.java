package com.minecolonies.vault.api.inheritance;

import com.minecolonies.vault.api.utils.NBTUtils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.jetbrains.annotations.NotNull;

import static com.minecolonies.vault.api.constants.ModNBTConstants.Trees.CONST_NBT_CHILDREN;
import static com.minecolonies.vault.api.constants.ModNBTConstants.Trees.CONST_NBT_DATA;
import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public interface ISaveableDataHoldingInheritanceTree<E extends ISaveableDataHoldingInheritanceTree<E, D, N>, D, N extends NBTBase> extends ISaveableInheritanceTree<E>, IReadWriteDataHoldingInheritanceTreeElement<E, D>
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

    /**
     * Method used to serialize the data that is stored in this element.
     * @return NBTTag representing the data stored in this element.
     */
    N serializeData();

    @Override
    default void deserializeNBT(NBTTagCompound nbt)
    {
        NBTTagList childrenList = nbt.getTagList(CONST_NBT_CHILDREN, TAG_COMPOUND);

        NBTUtils.streamCompound(childrenList).forEach(c -> {
            final ISaveableInheritanceTree<E> newInstance = getNewInstance();

            newInstance.deserializeNBT(c);
            newInstance.setParent(this);
        });

        N dataNbt = (N) nbt.getTag(CONST_NBT_DATA);
        D data = deserializeData(dataNbt);

        setData(data);
    }

    /**
     * Method used to deserialize the data that should be stored in this element, without setting the data stored in this element.
     * @param dataNbt The NBT of the data to deserialize.
     * @return The data stored in the NBT.
     */
    D deserializeData(@NotNull final N dataNbt);
}
