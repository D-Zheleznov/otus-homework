package ru.otus.gc.bench;

import java.util.ArrayList;
import java.util.List;

class Benchmark implements BenchmarkMBean {

    void run() throws InterruptedException {
        List<String> objects = new ArrayList<>();
        while (true) {
            for (int i = 0; i < 30000; i++)
                objects.add(new String(new char[0]));
            for (int i = 0; i < 100; i++)
                objects.remove(i);
            Thread.sleep(100);
        }
    }
}
