package com.minecolonies.vault.api.inheritance;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This interface describes common properties, methods and characteristics of {@link IInheritanceTreeElement elements} of an common Tree structure with inheritance.
 * @param <E> The type of element this is, also defines the type of the entire tree.
 */
public interface IInheritanceTreeElement<E extends IInheritanceTreeElement<E>>
{

    /**
     * Method to get the {@link E root} of this {@link E element} in the inheritance tree.
     * @return The {@link E root} of the inheritance tree.
     */
    @NotNull
    E getRoot();

    /**
     * Method to get the direct {@link E parent} of this {@link E element} in the inheritance tree.
     * @return The {@link E parent} of this {@link E element}
     */
    @NotNull
    E getParent();

    /**
     * Method used to set the {@link E parent} of this {@link E element}.
     * Removes this {@link E element} from the children list of the current {@link E parent}, if applicable, by calling the {@link #getParent()#removeChild} method.
     * Adds this {@link E element} to the children list of the given {@link E parent}, if applicable, by calling the {@link #getParent()#addChild} method.
     *
     * If null is passed as a new {@link E parent}, this {@link E element} is turned into the {@link E root} if all of its {@link E children}.
     *
     * @param parent The new {@link E parent} of this {@link E element}
     * @return The {@link E instance} this was called upon.
     */
    IInheritanceTreeElement<E> setParent(@Nullable final IInheritanceTreeElement<E> parent);

    /**
     * Method to get the {@link ImmutableList<E> children} of this {@link E element}.
     * @return The {@link ImmutableList<E> children} of this {@link E element}
     */
    ImmutableList<E> getChildren();

    /**
     * Method used to add a {@link E child} to this {@link E element} of the inheritance tree.
     * This {@link E child} has to be unique.
     *
     * @param child The new unique {@link E child} that is to be added to the tree as a child to this {@link E element}
     * @return The {@link E instance} this was called upon.
     * @throws IllegalArgumentException Thrown when either the child is already known, or its {@link #getParent()} is not equal to the current instance.
     */
    IInheritanceTreeElement<E> addChild(@NotNull final E child) throws IllegalArgumentException;

    /**
     * Method used to remove the given {@link E child} from the children of this {@link E element} and turn it into a new Tree.
     * To create the new tree its {@link #setParent(IInheritanceTreeElement)} is called with a null parameter.
     * @param child The {@link} child to remove and make a parent.
     * @return
     * @throws IllegalArgumentException
     */
    IInheritanceTreeElement<E> removeChild(@NotNull final E child) throws IllegalArgumentException;

    ImmutableList<E> getSiblings();

    ImmutableList<E> getSiblings(final int order);
}
