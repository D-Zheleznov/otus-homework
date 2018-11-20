package ru.otus.l021.memory.factory;

public class Class1 extends Abstract {

    private String string = new String("");

    private String string2 = new String(new char[0]);

    private long l = 100L;

    @Override
    public Object getInstance() {
        return new Class1();
    }
}
