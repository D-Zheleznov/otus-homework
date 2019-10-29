package ru.otus.l15.hibernate.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "USER")
public class User extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ADDRESS_DATA_SET_ID")
    private AddressDataSet addressDataSet;

    @OneToMany(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "USER_ID")
    private Collection<PhoneDataSet> phoneDataSet;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
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
