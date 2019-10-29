package ru.otus.l15.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS_DATA_SET")
public class AddressDataSet extends BaseEntity {

    @Column(name = "STREET", nullable = false)
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
        return "{" + "street='" + street + '\'' + super.toString() + "} ";
    }
}
