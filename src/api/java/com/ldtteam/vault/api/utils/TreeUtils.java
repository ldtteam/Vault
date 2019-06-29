package com.ldtteam.vault.api.utils;

import com.ldtteam.vault.api.inheritance.IInheritanceTreeElement;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

public class TreeUtils
{

    private static Logger treeUtilsLogger = LogUtils.constructLoggerForClass(TreeUtils.class);

    public static <E extends IInheritanceTreeElement<E>> int getTreeDepth(@NotNull final E tree)
    {
        return getTreeDepth(tree, new HashMap<>(), new LinkedList<>());
    }

    private static <E extends IInheritanceTreeElement<E>> int getTreeDepth(@NotNull final E tree, @NotNull Map<E, Integer> knownDepth, @NotNull LinkedList<E> calculationQueue)
    {
        if (knownDepth.containsKey(tree))
            return knownDepth.get(tree);

        if (calculationQueue.contains(tree))
        {
            treeUtilsLogger.error("Found circular dependency in tree. Returning Infinite!");
            knownDepth.put(tree, Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
        }

        calculationQueue.add(tree);

        int depth = tree.getChildren().stream().mapToInt(c -> getTreeDepth(c, knownDepth, calculationQueue)).max().orElse(0) + 1;

        calculationQueue.remove(tree);
        knownDepth.put(tree, depth);

        return depth;
    }

    @Nullable
    public static <E extends IInheritanceTreeElement<E>> E performDepthFirstSearch(@NotNull final E tree, @NotNull Predicate<E> matchingPredicate)
    {
        LinkedList<E> path = new LinkedList<>();
        performDepthFirstSearch(tree, matchingPredicate, path);

        E root = path.removeFirst().cloneAsEmptyRoot();
        E current;
        while (!path.isEmpty())
        {
            current = path.removeFirst().cloneAsEmptyRoot();
            current.setParent(current);
        }

        return root;
    }

    @Nullable
    private static <E extends IInheritanceTreeElement<E>> E performDepthFirstSearch(@NotNull final E tree, @NotNull Predicate<E> matchingPredicate, @NotNull final Queue<E> path)
    {
        path.add(tree);
        if (matchingPredicate.test(tree))
        {
            return tree;
        }

        E rootChild = tree.getChildren().stream().map(c -> performDepthFirstSearch(c, matchingPredicate, path)).filter(c -> c != null).findFirst().orElse(null);

        if (rootChild == null) {
            path.remove();
            return null;
        }

        return tree;
    }
}
