package ru.otus.l12.model;

public class SimplePojo {

    private String name;
    private int num;

    public SimplePojo(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public SimplePojo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
