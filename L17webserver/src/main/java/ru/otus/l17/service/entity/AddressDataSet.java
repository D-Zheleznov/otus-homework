package ru.otus.l17.service.entity;

public class AddressDataSet extends BaseEntity {

    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return this.street;
    }
}
