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
}
