package com.minecolonies.vault.api.inheritance;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This interface describes common properties, methods and characteristics of {@link IInheritanceTreeElement elements} of an common Tree structure with inheritance.
 * @param <E> The type of element this is, also defines the type of the entire tree.
 */
public interface IInheritanceTreeElement<E extends IInheritanceTreeElement<E>> extends Set<E>
{

    /**
     * Method to get the {@link E root} of this {@link E element} in the inheritance tree.
     * @return The {@link E root} of the inheritance tree.
     */
    @NotNull
    default E getRoot() {
        if (getParent() == null)
            return (E) this;

        return getParent().getRoot();
    }

    /**
     * Method used to check if this {@link E element} is the {@link E root} of the tree.
     * @return True when this is the {@link E root} false when not.
     */
    default boolean isRoot(){
        return getRoot().equals(this);
    }

    /**
     * Method to get the direct {@link E parent} of this {@link E element} in the inheritance tree.
     * If this {@link E element} is the {@link E root} of the tree it should return itself.
     * @return The {@link E parent} of this {@link E element}, or null if it is the root.
     */
    @Nullable
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
    E setParent(@Nullable final E parent);

    /**
     * Method to get the {@link ImmutableList<E> children} of this {@link E element}.
     * @return The {@link ImmutableList<E> children} of this {@link E element}
     */
    ImmutableSet<E> getChildren();

    /**
     * Method used to check if this is a leaf or not.
     * @return True when this is a leaf, false when not.
     */
    default boolean isLeaf() {
        return getChildren().size() == 0;
    }

    /**
     * Method used to add a {@link E child} to this {@link E element} of the inheritance tree.
     * This {@link E child} has to be unique.
     *
     * @param child The new unique {@link E child} that is to be added to the tree as a child to this {@link E element}
     * @return The {@link E instance} this was called upon.
     * @throws IllegalArgumentException Thrown when either the {@link E child} is already known, or its {@link #getParent()} is not equal to the current instance.
     */
    E addChild(@NotNull final E child) throws IllegalArgumentException;

    /**
     * Method used to remove the given {@link E child} from the children of this {@link E element} and turn it into a new Tree.
     *
     * @param child The {@link E child} to remove and make a new {@link E root}.
     * @return The instance this was called upon.
     * @throws IllegalArgumentException Thrown when the given {@link E child} is not known to this {@link E element.}
     */
    E removeChild(@NotNull final E child) throws IllegalArgumentException;

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
     default ImmutableSet<E> getSiblings() throws IllegalArgumentException {
         return getSiblings(1);
     }

    /**
     * Method used
     * @return
     * @throws IllegalArgumentException
     */
     default ImmutableSet<E> getMaximalOrderSiblings() throws IllegalArgumentException {
         return getSiblings(getElementDepth() - 1);
     }

    /**
     * Method used to get siblings from a given order.
     * @param order The order of siblings to check for.
     * @return A {@link ImmutableList<E> list of siblings} from given order.
     * @throws IllegalArgumentException Thrown when the tree is not of the required depth to have this order.
     */
    default ImmutableSet<E> getSiblings(final int order) throws IllegalArgumentException {
        if (order <= 0)
            return ImmutableSet.of();

        if (isRoot())
            throw new IllegalArgumentException("Can not get siblings of root, as root has no parent. Maybe your order was to big?");

        //Get all the aunt/uncles of that order.
        ImmutableSet<E> parentSiblings = getParent().getSiblings(order - 1);
        ImmutableSet.Builder<E> siblingsBuilder = ImmutableSet.builder();

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

    /**
     * Returns the maximal residual depth of this node.
     * Any leaf will have a residual depth of 0.
     *
     * @return The Maximal residual depth of this node.
     */
    default int getMaximalResidualDepth() {
        if (isLeaf())
            return 0;

        return getChildren().stream().mapToInt(c -> c.getMaximalResidualDepth()).max().orElse(-1) + 1;
    }

    /**
     * Method used to get a cloned empty element, without parent (so as root) and without children.
     * @return A new instance of this element, as empty root.
     */
    E cloneAsEmptyRoot();

    /**
     * Returns the deepest child matching the given criteria, or itself (if no child matches but this)
     * @param searchCriteria The criteria to search for.
     * @return The deepest child matching the criteria.
     */
    @Nullable
    default E getDeepestChild(Predicate<E> searchCriteria)
    {
        E matching = getChildren().stream().sorted(Comparator.comparing(c -> getMaximalResidualDepth())).map(c -> c.getDeepestChild(searchCriteria)).filter(r -> r != null).findFirst().orElse(null);

        if (matching != null)
        {
            return matching;
        }

        if (searchCriteria.test((E) this))
        {
            return (E) this;
        }

        return null;
    }

    @Override
    default int size()
    {
        return getChildren().stream().mapToInt(IInheritanceTreeElement::size).sum() + 1;
    }


    @Override
    default boolean isEmpty()
    {
        return false;
    }


    @Override
    default boolean contains(Object o)
    {
        if (this.equals(o))
            return true;

        return getChildren().stream().anyMatch(c -> c.contains(o));
    }

    default ImmutableSet<E> getAllTreeElements()
    {
        final ImmutableSet.Builder<E> builder = ImmutableSet.builder();

        builder.add((E) this);
        getChildren().forEach(c -> builder.addAll(c.getAllTreeElements()));

        return builder.build();
    }

    @NotNull
    @Override
    default Iterator<E> iterator()
    {
        return getAllTreeElements().iterator();
    }

    @NotNull
    @Override
    default Object[] toArray()
    {
        return getAllTreeElements().toArray();
    }

    @NotNull
    @Override
    default <T> T[] toArray(@NotNull T[] a)
    {
        return getAllTreeElements().toArray(a);
    }

    @Override
    default boolean add(E e)
    {
        this.addChild(e);
        return true;
    }

    @Override
    default boolean remove(Object o)
    {
        this.removeChild((E) o);
        return true;
    }

    @Override
    default boolean containsAll(@NotNull Collection<?> c)
    {
        return !c.stream().map(this::contains).filter(b -> !b).findAny().orElse(true);
    }

    @Override
    default boolean addAll(@NotNull Collection<? extends E> c)
    {
        return c.stream().anyMatch(this::add);
    }

    @Override
    default boolean retainAll(@NotNull Collection<?> c)
    {
        final Set<E> childrenCopy = new HashSet<>(getChildren());

        boolean rootModified = childrenCopy.stream().filter(o -> !c.contains(o)).map(o -> o.setParent(null)).findAny().orElse(null) != null;
        boolean residualChildrenModified = getChildren().stream().anyMatch(o -> o.retainAll(c));

        return rootModified || residualChildrenModified;
    }

    @Override
    default boolean removeAll(@NotNull Collection<?> c)
    {
        final Set<E> childrenCopy = new HashSet<>(getChildren());

        boolean rootModified = childrenCopy.stream().filter(o -> c.contains(o)).map(o -> o.setParent(null)).findAny().orElse(null) != null;
        boolean residualChildrenModified = getChildren().stream().anyMatch(o -> o.removeAll(c));

        return rootModified || residualChildrenModified;
    }

    @Override
    default void clear()
    {
         final Set<E> childrenCopy = new HashSet<>(getChildren());
         childrenCopy.forEach(c -> c.setParent(null));
    }
}
