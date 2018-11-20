package ru.otus.l021.memory;

import ru.otus.l021.memory.factory.Abstract;
import ru.otus.l021.memory.factory.Class1;
import ru.otus.l021.memory.factory.Class2;
import ru.otus.l021.memory.factory.Class3;

import java.lang.management.ManagementFactory;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws InterruptedException {
//        init(new Class1());
//        init(new Class2());
        init(new Class3());
    }

    private static void init(Abstract obj) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        int size = 20_000_000;
        System.out.println("Начало цикла");

        while (true) {
            long mem = getMemory();
            System.out.println("Общее количество памяти: " + mem);

            Object[] array = new Object[size];

            long mem2 = getMemory();
            System.out.println("Размер пустого массива: " + (mem2 - mem) / array.length);

            for (int i = 0; i < array.length; i++) {
                array[i] = obj.getInstance();
            }

            long mem3 = getMemory();
            System.out.println("Размер элемента: " + (mem3 - mem2) / array.length);

            array = null;
            System.out.println("Массив готов к сбору сборщиком мусора");
            System.out.println("--------------------------------------");
            Thread.sleep(1000);
        }
    }

    private static long getMemory() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
