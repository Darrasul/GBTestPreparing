package org.buzas.lesson2.question2;

import lombok.AccessLevel;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.RandomAccess;

public class MyArrayList<E> implements RandomAccess, Cloneable, Serializable {
    private final int DEFAULT_CAP = 10;

    @Getter(AccessLevel.PUBLIC)
    private transient Object[] elements;
    private final Object[] DEFAULT_EMPTY_ELEMENTS = {};
    @Getter(AccessLevel.PUBLIC)
    private int size;

    public MyArrayList() {
        this.elements = DEFAULT_EMPTY_ELEMENTS;
    }

    public MyArrayList(int cap) {
        if (cap > 0) {
            this.elements = new Object[cap];
            this.size = cap;
        } else if (cap == 0) {
            this.elements = DEFAULT_EMPTY_ELEMENTS;
        } else {
            throw new IllegalArgumentException("Capacity of List need to be more than 0/equal 0. Now it's " + cap);
        }
    }

    public void increaseCap(int cap) {
        if (cap > this.size) {
            this.size = cap;
            grow(cap);
        } else {
            throw new IllegalArgumentException("You cannot increase the size of the list in a way that makes it smaller. Current list size: " + size);
        }
    }

    public boolean isEmpty() {
        return Arrays.equals(elements, DEFAULT_EMPTY_ELEMENTS);
    }

    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elements[index];
    }

    public void set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldElement = (E) elements[index];
        try {
            this.elements[index] = element;
        } catch (Exception e) {
            this.elements[index] = oldElement;
            e.printStackTrace();
        }
    }

    public void add(E element) {
        add(element, this.elements, this.size);
    }

    public void addAll(Collection<E> collection) {
        Object[] addedObj = collection.toArray();
        addAll(addedObj);
    }

    private void addAll(Object[] objects) {
        int addedCount = objects.length;
        if (addedCount == 0) {
            throw new IllegalArgumentException("Trying to add an empty collection");
        }
        if (addedCount > this.elements.length - this.size) {
            this.elements = grow(size + addedCount);
        }
        System.arraycopy(objects, 0, this.elements, this.size, addedCount);
        this.size = this.size + addedCount;
    }

    public void remove(int index) {
        Objects.checkIndex(index, this.size);
        Object[] elements = this.elements;
        remove(elements, index);
    }

    public void clear() {
        this.elements = new Object[]{};
        this.size = this.elements.length;
    }

    private Object[] grow(int cap) {
        return elements = Arrays.copyOf(this.elements, cap);
    }

    private Object[] grow() {
        return grow(this.size + 1);
    }

    private void add(E element, Object[] array, int arraySize) {
        if (arraySize == array.length) {
            array = grow();
        }
        array[arraySize] = element;
        this.size = arraySize + 1;
    }

    private void remove(Object[] elements, int index) {
        Object[] first = Arrays.copyOfRange(elements, 0, index);
        Object[] second = Arrays.copyOfRange(elements, index + 1, elements.length);
        clear();
        addAll(first);
        addAll(second);
    }


    @Override
    public MyArrayList<E> clone() {
        try {
            MyArrayList<E> clone = (MyArrayList<E>) super.clone();
            clone.elements = Arrays.copyOf(this.elements, size);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
