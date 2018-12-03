package ru.otus.l021.memory;

public class Main {
    public static void main(String... args) {
        ObjectSizeMeter objectSizeMeter = new ObjectSizeMeter();

        objectSizeMeter.getObjectSize(new ObjectFactory(() -> new String()));
        objectSizeMeter.getObjectSize(new ObjectFactory(() -> new String("Hello Otus!")));
        objectSizeMeter.getObjectSize(new ObjectFactory(() -> new int[0]));
        objectSizeMeter.getObjectSize(new ObjectFactory(() -> new String(new byte[0])));
        objectSizeMeter.getObjectSize(new ObjectFactory(() -> new String(new char[0])));
        objectSizeMeter.getObjectSize(null);
        objectSizeMeter.getObjectSize(new ObjectFactory<>(null));
    }
}