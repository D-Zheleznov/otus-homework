package ru.otus.l15.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PHONE_DATA_SET")
public class PhoneDataSet extends BaseEntity {

    @Column(name = "NUMBER", nullable = false)
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "{" + "number='" + number + '\'' + super.toString() + "}";
    }
}
