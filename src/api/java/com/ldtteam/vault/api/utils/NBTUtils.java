package com.ldtteam.vault.api.utils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NBTUtils
{

    public static Stream<NBTBase> streamBase(NBTTagList list)
    {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new TagListIterator(list), Spliterator.ORDERED), false);
    }

    public static Stream<NBTTagCompound> streamCompound(NBTTagList list)
    {
        return streamBase(list).filter(b -> b instanceof NBTTagCompound).map(b -> (NBTTagCompound) b);
    }

    public static Collector<NBTTagCompound, NBTTagList, NBTTagList> toNBTTagList() {
        return new Collector<NBTTagCompound, NBTTagList, NBTTagList>() {
            @Override
            public Supplier<NBTTagList> supplier()
            {
                return NBTTagList::new;
            }

            @Override
            public BiConsumer<NBTTagList, NBTTagCompound> accumulator()
            {
                return NBTTagList::appendTag;
            }

            @Override
            public BinaryOperator<NBTTagList> combiner()
            {
                return (list1, list2) -> {
                    NBTTagList result = supplier().get();

                    streamBase(list1).forEach(result::appendTag);
                    streamBase(list2).forEach(result::appendTag);

                    return result;
                };
            }

            @Override
            public Function<NBTTagList, NBTTagList> finisher()
            {
                return (nbtTagList -> nbtTagList);
            }

            @Override
            public Set<Characteristics> characteristics()
            {
                return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH));
            }
        };
    }

    public static NBTTagList writeBoundingBox(@NotNull final AxisAlignedBB bb)
    {
        NBTTagList data = new NBTTagList();
        data.appendTag(new NBTTagDouble(bb.minX));
        data.appendTag(new NBTTagDouble(bb.minY));
        data.appendTag(new NBTTagDouble(bb.minZ));
        data.appendTag(new NBTTagDouble(bb.maxX));
        data.appendTag(new NBTTagDouble(bb.maxY));
        data.appendTag(new NBTTagDouble(bb.maxZ));

        return data;
    }

    public static AxisAlignedBB readBoundingBox(@NotNull final NBTTagList bb)
    {
        double minX = bb.getDoubleAt(0);
        double minY = bb.getDoubleAt(1);
        double minZ = bb.getDoubleAt(2);
        double maxX = bb.getDoubleAt(3);
        double maxY = bb.getDoubleAt(4);
        double maxZ = bb.getDoubleAt(5);

        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    private static class TagListIterator implements Iterator<NBTBase>{

        private int currentIndex = 0;
        private final NBTTagList list;

        private TagListIterator(final NBTTagList list) {this.list = list;}

        @Override
        public boolean hasNext()
        {
            return currentIndex < list.tagCount();
        }

        @Override
        public NBTBase next()
        {
            return list.getCompoundTagAt(currentIndex++);
        }
    }


}
