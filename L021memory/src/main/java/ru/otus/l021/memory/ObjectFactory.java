package ru.otus.l021.memory;

import java.util.function.Supplier;

class ObjectFactory<T> {

    private Supplier<T> supplier;

    ObjectFactory(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    Object[] getArrayOfObject(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = supplier.get();
        }
        return arr;
    }

    int[] getArrayOfInt() {
        int[] intArray = new int[20000000];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = i;
        }
        return intArray;
    }

    long[] getArrayOfLong() {
        long[] longArray = new long[20000000];
        for (int i = 0; i < longArray.length; i++) {
            longArray[i] = i;
        }
        return longArray;
    }
}
