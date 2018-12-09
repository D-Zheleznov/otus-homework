package ru.otus.l031.arrayList;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private Object[] elementData;
    private int size;

    public MyArrayList() {
        this.elementData = new Object[]{};
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        if (array.length < size)
            return (T[]) Arrays.copyOf(elementData, size, array.getClass());

        System.arraycopy(elementData, 0, array, 0, size);
        if (array.length > size)
            array[size] = null;

        return array;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T t) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = t;
        return true;
    }

    @Override
    public T set(int index, T element) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        T oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    private T elementData(int index) {
        return (T) elementData[index];
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == new Object[]{})
            minCapacity = Math.max(10, minCapacity);

        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void sort(Comparator<? super T> c) {
        Object[] array = this.toArray();
        Arrays.sort(array, (Comparator) c);
        ListIterator<T> iterator = this.listIterator();
        for (Object obj : array) {
            iterator.next();
            iterator.set((T) obj);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    private class Itr implements Iterator<T> {

        int cursor;
        int lastRet = -1;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {

        ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public void set(T t) {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                MyArrayList.this.set(lastRet, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void remove() {
        }

        public boolean hasPrevious() {
            return false;
        }

        public int nextIndex() {
            return 0;
        }

        public int previousIndex() {
            return 0;
        }

        public T previous() {
            return null;
        }

        public void add(T t) {

        }
    }
////////////////////////////////////////////////////////////////////////////////////////

    public ListIterator<T> listIterator(int index) {
        return null;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {

    }

    public T get(int index) {
        return null;
    }

    public void add(int index, T element) {

    }

    public T remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }
}
