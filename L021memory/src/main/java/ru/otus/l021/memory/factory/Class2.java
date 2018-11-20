package ru.otus.l021.memory.factory;

public class Class2 extends Abstract {

    private String string = new String(new byte[0]);

    private byte[] aByte = new byte[1];

    private byte b = 1;

    private int i = 10;

    @Override
    public Object getInstance() {
        return new Class2();
    }
}
