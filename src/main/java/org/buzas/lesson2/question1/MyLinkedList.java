package org.buzas.lesson2.question1;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.module.FindException;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ListIterator;

public class MyLinkedList<E> extends AbstractSequentialList<E> implements Cloneable, Serializable {

    transient Node<E> first;
    transient Node<E> last;
    transient int size = 0;

    public MyLinkedList() {
    }

    @Override
    public int size() {
        return size;
    }

    public void addFirst(E element) {
        linkFirst(element);
    }

    public void addLast(E element) {
        linkLast(element);
    }

    public E getFirst() {
        Node<E> first = this.first;
        if (first == null) {
            throw new FindException("There are no first element");
        }
        return first.getElement();
    }

    public E getLast() {
        Node<E> last = this.last;
        if (last == null) {
            throw new FindException("There are no last element");
        }
        return last.getElement();
    }

    public void removeFirst() {
        Node<E> first = this.first;
        if (first == null) {
            throw new FindException("There are no first element");
        }
        unlinkFirst(first);
    }

    public void removeLast() {
        Node<E> last = this.last;
        if (last == null) {
            throw new FindException("There are no last element");
        }
        unlinkLast(last);
    }

    public boolean add(E element) {
        linkLast(element);
        return true;
    }

    public E set(int index, E element) {
        if (index > this.size) {
            throw new FindException("There are no such index like " + index + " . Cause by: max index is " + size);
        }
        Node<E> oldElement = node(index);
        oldElement.setElement(element);
        return element;
    }

    public E get(int index) {
        if (index > this.size) {
            throw new FindException("There are no such index like " + index + " . Cause by: max index is " + size);
        }
        return node(index).getElement();
    }

    public boolean remove(Object object) {
        if (object == null) {
            for (Node<E> node = first; node != null; node = node.getNext()) {
                if (node.getElement() == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.getNext()) {
                if (object.equals(node.getElement())){
                    unlink(node);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addAll(Collection<? extends E> collection) {
        for (E element : collection) {
            add(element);
        }
        return true;
    }

    private void linkFirst(E element) {
        Node<E> first = this.first;
        Node<E> newFirst = new Node<>(element, null, first);
        this.first = newFirst;
        if (first == null) {
            this.last = newFirst;
        } else {
            first.setPrevious(newFirst);
        }
        size++;
    }

    private void linkLast(E element) {
        Node<E> last = this.last;
        Node<E> newLast = new Node<>(element, last, null);
        this.last = newLast;
        if (last == null) {
            this.first = newLast;
        } else {
            last.setNext(newLast);
        }
        size++;
    }


    private void linkBefore(E element, Node<E> plugged) {
        Node<E> prev = plugged.getPrevious();
        Node<E> newPrev = new Node<>(element, prev, plugged);
        plugged.setPrevious(newPrev);
        if (prev == null) {
            this.first = newPrev;
        } else {
            prev.setNext(newPrev);
        }
        size++;
    }

    private void unlinkFirst(Node<E> element) {
        Node<E> next = element.getNext();
        element.setElement(null);
        element.setNext(null);
        this.first = next;
        if (next == null) {
            this.last = null;
        } else {
            next.setPrevious(null);
        }
        size--;
    }
    private void unlinkLast(Node<E> element) {
        Node<E> previous = element.getPrevious();
        element.setElement(null);
        element.setPrevious(null);
        this.last = previous;
        if (previous == null) {
            this.first = null;
        } else {
            previous.setNext(null);
        }
        size--;
    }

    private void unlink(Node<E> element) {
        Node<E> next = element.getNext();
        Node<E> previous = element.getPrevious();
        if (previous == null) {
            this.first = next;
        } else {
            previous.setNext(next);
            element.setPrevious(null);
        }
        if (next == null) {
            this.last = previous;
        } else {
            next.setPrevious(previous);
            element.setNext(null);
        }
        element.setElement(null);
        size--;
    }

    private Node<E> node(int index) {
        Node<E> element;
        if (index < (this.size >> 1)) {
            element = this.first;
            for (int i = 0; i < index; i++) {
                element = element.getNext();
            }
        } else {
            element = this.last;
            for (int i = size - 1; i > index; i--) {
                element = element.getPrevious();
            }
        }
        return element;
    }

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private static class Node<E> {

        E element;

        Node<E> next;
        Node<E> previous;
        public Node(E element, Node<E> previous, Node<E> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyIterator(index);
    }
//  Если прошлое я писал иногда заглядывая в ArrayList и LinkedList в моментах, когда откровенно застревал или налетал на ошибки,
//  то здесь чистая копия итератора из LinkedList. Я понимаю как он работает более-менее, но написать его грамотно сам не смог
    private class MyIterator implements ListIterator<E> {

        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        public MyIterator(int index) {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new FindException();
            }
            lastReturned = next;
            next = next.getNext();
            nextIndex++;
            return lastReturned.getElement();
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new FindException();
            }

            lastReturned = next = (next == null)? last : next.getPrevious();
            nextIndex--;
            return lastReturned.getElement();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            Node<E> lastNext = lastReturned.getNext();
            unlink(lastReturned);
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = null;
            expectedModCount++;
        }

        @Override
        public void set(E element) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.setElement(element);
        }

        @Override
        public void add(E element) {
            lastReturned = null;
            if (next == null) {
                linkLast(element);
            } else {
                linkBefore(element, next);
            }
            nextIndex++;
            expectedModCount++;
        }
    }
}
