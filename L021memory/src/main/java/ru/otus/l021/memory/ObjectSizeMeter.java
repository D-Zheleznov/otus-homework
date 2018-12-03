package ru.otus.l021.memory;

class ObjectSizeMeter {

    void getObjectSize(ObjectFactory factory) {
        Object[] array = new Object[20000000];
        long memorySizeBefore = getMemory();
        System.gc();
        System.out.println("Размер памяти до заполнения массива: " + memorySizeBefore);

        Object[] filledArray = factory == null ? array : factory.getArrayOfObject(array);

        long elementMemorySize = (getMemory() - memorySizeBefore) / filledArray.length;
        System.out.println("Размер элемента: " + elementMemorySize);
    }

    private static long getMemory() {
        System.gc();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
