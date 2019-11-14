package ru.otus.l17.service.entity;

import java.util.Collection;
import java.util.UUID;

public class User extends BaseEntity {

    private String name;
    private Integer age;
    private AddressDataSet addressDataSet;
    private Collection<PhoneDataSet> phoneDataSet;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public AddressDataSet getAddressDataSet() {
        return addressDataSet;
    }

    public void setAddressDataSet(AddressDataSet addressDataSet) {
        this.addressDataSet = addressDataSet;
    }

    public Collection<PhoneDataSet> getPhoneDataSet() {
        return phoneDataSet;
    }

    public void setPhoneDataSet(Collection<PhoneDataSet> phoneDataSet) {
        this.phoneDataSet = phoneDataSet;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", age=" + age + ", addressDataSet=" + addressDataSet + ", phoneDataSet=" + phoneDataSet + ", " + super.toString() + "}";
    }
}
