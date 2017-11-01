package com.minecolonies.vault.api.inheritance;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

/**
 * This interface describes common properties, methods and characteristics of {@link IInheritanceTreeElement elements} of an common Tree structure with inheritance.
 * @param <E> The type of element this is, also defines the type of the entire tree.
 */
public interface IInheritanceTreeElement<E extends IInheritanceTreeElement<E>>
{

    /**
     * Method to get the {@link IInheritanceTreeElement<E> root} of this {@link IInheritanceTreeElement<E> element} in the inheritance tree.
     * @return The {@link IInheritanceTreeElement<E> root} of the inheritance tree.
     */
    @NotNull
    IInheritanceTreeElement<E> getRoot();

    /**
     * Method used to check if this {@link IInheritanceTreeElement<E> element} is the {@link IInheritanceTreeElement<E> root} of the tree.
     * @return True when this is the {@link IInheritanceTreeElement<E> root} false when not.
     */
    default boolean isRoot(){
        return getRoot().equals(this);
    }

    /**
     * Method to get the direct {@link IInheritanceTreeElement<E> parent} of this {@link IInheritanceTreeElement<E> element} in the inheritance tree.
     * If this {@link IInheritanceTreeElement<E> element} is the {@link IInheritanceTreeElement<E> root} of the tree it should return itself.
     * @return The {@link IInheritanceTreeElement<E> parent} of this {@link IInheritanceTreeElement<E> element}
     */
    @NotNull
    IInheritanceTreeElement<E> getParent();

    /**
     * Method used to set the {@link IInheritanceTreeElement<E> parent} of this {@link IInheritanceTreeElement<E> element}.
     * Removes this {@link IInheritanceTreeElement<E> element} from the children list of the current {@link IInheritanceTreeElement<E> parent}, if applicable, by calling the {@link #getParent()#removeChild} method.
     * Adds this {@link IInheritanceTreeElement<E> element} to the children list of the given {@link IInheritanceTreeElement<E> parent}, if applicable, by calling the {@link #getParent()#addChild} method.
     *
     * If null is passed as a new {@link IInheritanceTreeElement<E> parent}, this {@link IInheritanceTreeElement<E> element} is turned into the {@link IInheritanceTreeElement<E> root} if all of its {@link IInheritanceTreeElement<E> children}.
     *
     * @param parent The new {@link IInheritanceTreeElement<E> parent} of this {@link IInheritanceTreeElement<E> element}
     * @return The {@link IInheritanceTreeElement<E> instance} this was called upon.
     */
    IInheritanceTreeElement<E> setParent(@Nullable final IInheritanceTreeElement<E> parent);

    /**
     * Method to get the {@link ImmutableList<IInheritanceTreeElement<E>> children} of this {@link IInheritanceTreeElement<E> element}.
     * @return The {@link ImmutableList<IInheritanceTreeElement<E>> children} of this {@link IInheritanceTreeElement<E> element}
     */
    ImmutableSet<IInheritanceTreeElement<E>> getChildren();

    /**
     * Method used to check if this is a leaf or not.
     * @return True when this is a leaf, false when not.
     */
    default boolean isLeaf() {
        return getChildren().size() == 0;
    }

    /**
     * Method used to add a {@link IInheritanceTreeElement<E> child} to this {@link IInheritanceTreeElement<E> element} of the inheritance tree.
     * This {@link IInheritanceTreeElement<E> child} has to be unique.
     *
     * @param child The new unique {@link IInheritanceTreeElement<E> child} that is to be added to the tree as a child to this {@link IInheritanceTreeElement<E> element}
     * @return The {@link IInheritanceTreeElement<E> instance} this was called upon.
     * @throws IllegalArgumentException Thrown when either the {@link IInheritanceTreeElement<E> child} is already known, or its {@link #getParent()} is not equal to the current instance.
     */
    IInheritanceTreeElement<E> addChild(@NotNull final IInheritanceTreeElement<E> child) throws IllegalArgumentException;

    /**
     * Method used to remove the given {@link E child} from the children of this {@link IInheritanceTreeElement<E> element} and turn it into a new Tree.
     * To create the new tree its {@link #setParent(IInheritanceTreeElement)} is called with a null parameter.
     * @param child The {@link IInheritanceTreeElement<E> child} to remove and make a new {@link IInheritanceTreeElement<E> root}.
     * @return The instance this was called upon.
     * @throws IllegalArgumentException Thrown when the given {@link IInheritanceTreeElement<E> child} is not known to this {@link IInheritanceTreeElement<E> element.}
     */
    IInheritanceTreeElement<E> removeChild(@NotNull final IInheritanceTreeElement<E> child) throws IllegalArgumentException;

    /**
     * Method used to get the depth of the element.
     * @return The depth of the element. Depth of root is 1
     */
    default int getElementDepth()
    {
        if (isRoot())
            return 1;

        return getParent().getElementDepth() + 1;
    }

    /**
     * Method to get the Siblings of the first order.
     * Equivalent to {@link #getSiblings(int)} with 1 as parameter.
     * @return A {@link ImmutableList<E> list of siblings} from the first order.
     * @throws IllegalArgumentException when this is the root of a tree.
     */
     default ImmutableSet<IInheritanceTreeElement<E>> getSiblings() throws IllegalArgumentException {
         return getSiblings(1);
     }

    /**
     * Method used
     * @return
     * @throws IllegalArgumentException
     */
     default ImmutableSet<IInheritanceTreeElement<E>> getMaximalOrderSiblings() throws IllegalArgumentException {
         return getSiblings(getElementDepth() - 1);
     }

    /**
     * Method used to get siblings from a given order.
     * @param order The order of siblings to check for.
     * @return A {@link ImmutableList<E> list of siblings} from given order.
     * @throws IllegalArgumentException Thrown when the tree is not of the required depth to have this order.
     */
    default ImmutableSet<IInheritanceTreeElement<E>> getSiblings(final int order) throws IllegalArgumentException {
        if (order <= 0)
            return ImmutableSet.of();

        if (isRoot())
            throw new IllegalArgumentException("Can not get siblings of root, as root has no parent. Maybe your order was to big?");

        //Get all the aunt/uncles of that order.
        ImmutableSet<IInheritanceTreeElement<E>> parentSiblings = getParent().getSiblings(order - 1);
        ImmutableSet.Builder<IInheritanceTreeElement<E>> siblingsBuilder = ImmutableSet.builder();

        //Get all the children of every aunt and uncle.
        //Making sure to not add ourselfs.
        parentSiblings.forEach(parentSibling -> {
            siblingsBuilder.addAll(parentSibling.getChildren().stream().filter(sibling -> !sibling.equals(this)).collect(Collectors.toSet()));
        });

        //Get my first order siblings and add those, exclude my self.
        siblingsBuilder.addAll(getParent().getChildren().stream().filter(sibling -> !sibling.equals(this)).collect(Collectors.toSet()));

        //Build the sibling set.
        return siblingsBuilder.build();
    }



    default
}
