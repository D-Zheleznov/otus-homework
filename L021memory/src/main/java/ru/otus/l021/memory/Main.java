package ru.otus.l021.memory;

import java.util.function.Supplier;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) {
       Factory.getSize(Object::new);
       Factory.getSize(() -> new String(""));
       Factory.getSize(() -> new String(new char[0]));
       Factory.getSize(() -> new byte[0]);
       Factory.getSize(MyClass::new);
       Factory.getSize(() -> 666);
       Factory.getSize(() -> 555L);
    }

    static long getMemory() {
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

class Factory {

   static <T> void getSize(Supplier<T> object){
       Object[] array;
       int[] intArray;
       long[] longArray;

       if (object.get() instanceof Integer) {
           long memorySizeBefore = Main.getMemory();
           System.out.println("Размер памяти до создания массива int: " + memorySizeBefore);
           intArray = new int[20000000];
           for (int i = 0; i < intArray.length; i++) {
               intArray[i] = i;
           }
           long elementMemorySize = (Main.getMemory() - memorySizeBefore) / intArray.length;
           System.out.println("Размер элемента: " + elementMemorySize);
       }
       else if (object.get() instanceof Long) {
           long memorySizeBefore = Main.getMemory();
           System.out.println("Размер памяти до создания массива long: " + memorySizeBefore);
           longArray = new long[20000000];
           for (int i = 0; i < longArray.length; i++) {
               longArray[i] = i;
           }
           long elementMemorySize = (Main.getMemory() - memorySizeBefore) / longArray.length;
           System.out.println("Размер элемента: " + elementMemorySize);
       }
       else {
           array = new Object[20000000];
           long memorySizeBefore = Main.getMemory();
           System.out.println("Размер памяти с пустым массивом Object: " + memorySizeBefore);
           for (int i = 0; i < array.length; i++) {
               array[i] = object.get();
           }
           long elementMemorySize = (Main.getMemory() - memorySizeBefore) / array.length;
           System.out.println("Размер элемента: " + elementMemorySize);
       }
   }
}

class MyClass {
    private byte b = 0;
    private int i = 0;
    private long l = 1;
}