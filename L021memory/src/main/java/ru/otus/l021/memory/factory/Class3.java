package ru.otus.l021.memory.factory;

public class Class3 extends Abstract {

    private MyClass myClass = new MyClass();

    @Override
    public Object getInstance() {
        return new Class3();
    }

    private static class MyClass {
        private byte b = 0;
        private int i = 0;
        private long l = 1;
    }
}