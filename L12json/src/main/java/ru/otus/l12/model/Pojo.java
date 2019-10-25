package ru.otus.l12.model;

import java.util.Collection;
import java.util.Map;

public class Pojo {

    private String string;
    private int integer;
    private SimplePojo simplePojo;
    private String[] strings;
    private int[] ints;
    private SimplePojo[] simplePojos;
    private Collection collection;
    private Map<Object, Object> simpleMap;
    private Map<Object, Object> objectMap;

    public Pojo() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public SimplePojo getSimplePojo() {
        return simplePojo;
    }

    public void setSimplePojo(SimplePojo simplePojo) {
        this.simplePojo = simplePojo;
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public SimplePojo[] getSimplePojos() {
        return simplePojos;
    }

    public void setSimplePojos(SimplePojo[] simplePojos) {
        this.simplePojos = simplePojos;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Map<Object, Object> getSimpleMap() {
        return simpleMap;
    }

    public void setSimpleMap(Map<Object, Object> simpleMap) {
        this.simpleMap = simpleMap;
    }

    public Map<Object, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<Object, Object> objectMap) {
        this.objectMap = objectMap;
    }
}
