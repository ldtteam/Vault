package com.minecolonies.vault.api.inheritance;

import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class IInheritanceTreeElementTest
{
    
    private final TestTreeImplementation root = new TestTreeImplementation();
    private final TestTreeImplementation rootChild1 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild1Child = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2Child1 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2Child2 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2Child3 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2Child1Child1 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2Child1Child2 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2Child3Child1 = new TestTreeImplementation();
    private final TestTreeImplementation rootChild2Child3Child2 = new TestTreeImplementation();

    @Before
    public void setUp() throws Exception
    {
        rootChild2Child3Child1.setParent(rootChild2Child3);
        rootChild2Child3Child2.setParent(rootChild2Child3);

        rootChild2Child1Child1.setParent(rootChild2Child1);
        rootChild2Child1Child2.setParent(rootChild2Child1);

        rootChild1Child.setParent(rootChild1);
        
        rootChild2Child1.setParent(rootChild2);
        rootChild2Child2.setParent(rootChild2);
        rootChild2Child3.setParent(rootChild2);
        
        rootChild1.setParent(root);
        rootChild2.setParent(root);        
    }

    @Test
    public void getRoot() throws Exception
    {
        assertEquals(root, root.getRoot());

        assertEquals(root, rootChild1.getRoot());
        assertEquals(root, rootChild2.getRoot());
        
        assertEquals(root, rootChild1Child.getRoot());
        
        assertEquals(root, rootChild2Child1.getRoot());
        assertEquals(root, rootChild2Child2.getRoot());
        assertEquals(root, rootChild2Child3.getRoot());

        assertEquals(root, rootChild2Child1Child1.getRoot());
        assertEquals(root, rootChild2Child1Child2.getRoot());

        assertEquals(root, rootChild2Child3Child1.getRoot());
        assertEquals(root, rootChild2Child3Child2.getRoot());
    }

    @Test
    public void isRoot() throws Exception
    {
        assertEquals(true, root.isRoot());

        assertEquals(false, rootChild1.isRoot());
        assertEquals(false, rootChild2.isRoot());

        assertEquals(false, rootChild1Child.isRoot());

        assertEquals(false, rootChild2Child1.isRoot());
        assertEquals(false, rootChild2Child2.isRoot());
        assertEquals(false, rootChild2Child3.isRoot());

        assertEquals(false, rootChild2Child1Child1.isRoot());
        assertEquals(false, rootChild2Child1Child2.isRoot());

        assertEquals(false, rootChild2Child3Child1.isRoot());
        assertEquals(false, rootChild2Child3Child2.isRoot());
    }

    @Test
    public void getParent() throws Exception
    {
        assertEquals(null, root.getParent());

        assertEquals(root, rootChild1.getParent());
        assertEquals(root, rootChild2.getParent());

        assertEquals(rootChild1, rootChild1Child.getParent());

        assertEquals(rootChild2, rootChild2Child1.getParent());
        assertEquals(rootChild2, rootChild2Child2.getParent());
        assertEquals(rootChild2, rootChild2Child3.getParent());

        assertEquals(rootChild2Child1, rootChild2Child1Child1.getParent());
        assertEquals(rootChild2Child1, rootChild2Child1Child2.getParent());

        assertEquals(rootChild2Child3, rootChild2Child3Child1.getParent());
        assertEquals(rootChild2Child3, rootChild2Child3Child2.getParent());
    }

    @Test
    public void setParent() throws Exception
    {

    }

    @Test
    public void getChildren() throws Exception
    {
    }

    @Test
    public void isLeaf() throws Exception
    {
    }

    @Test
    public void addChild() throws Exception
    {
    }

    @Test
    public void removeChild() throws Exception
    {
    }

    @Test
    public void getElementDepth() throws Exception
    {
    }

    @Test
    public void getSiblings() throws Exception
    {
    }

    @Test
    public void getMaximalOrderSiblings() throws Exception
    {
    }

    @Test
    public void getSiblings1() throws Exception
    {
    }

    /**
     * Test implementation of the {@link IInheritanceTreeElement} interface.
     */
    private class TestTreeImplementation implements IInheritanceTreeElement<TestTreeImplementation>
    {

        IInheritanceTreeElement<TestTreeImplementation>       parent;
        Set<IInheritanceTreeElement<TestTreeImplementation>> children = new HashSet<>();
        
        @Nullable
        @Override
        public IInheritanceTreeElement<TestTreeImplementation> getParent()
        {
            return parent;
        }

        @Override
        public IInheritanceTreeElement<TestTreeImplementation> setParent(@Nullable final IInheritanceTreeElement<TestTreeImplementation> parent)
        {
            if (getParent() != null)
                getParent().removeChild(this);
            
            this.parent = parent;
            
            if (getParent() != null)
                getParent().addChild(this);
            
            return this;
        }

        @Override
        public ImmutableSet<IInheritanceTreeElement<TestTreeImplementation>> getChildren()
        {
            return ImmutableSet.copyOf(children);
        }

        @Override
        public IInheritanceTreeElement<TestTreeImplementation> addChild(@NotNull final IInheritanceTreeElement<TestTreeImplementation> child)
          throws IllegalArgumentException
        {
            if (children.contains(child) || child.getParent() != this)
                throw new IllegalArgumentException("Child already known, or parent is not correctly set.");
            
            children.add(child);
            
            return this;
        }

        @Override
        public IInheritanceTreeElement<TestTreeImplementation> removeChild(@NotNull final IInheritanceTreeElement<TestTreeImplementation> child)
          throws IllegalArgumentException
        {
            if (!children.contains(child) || child.getParent() != this)
                throw new IllegalArgumentException("Child is not known, or parent is not correctly set.");
            
            children.remove(child);
            
            return this;
        }
    }
}