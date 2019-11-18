package ru.otus.l19.main;

import ru.otus.l19.thread.ThreadClass;

public class MainClass {

    public static void main(String[] args) {
        Thread thread1 = new ThreadClass();
        Thread thread2 = new ThreadClass();

        thread1.setName("\nПоток 1: ");
        thread2.setName(" Поток 2: ");

        thread1.start();
        thread2.start();
    }
}
