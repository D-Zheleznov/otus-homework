package ru.otus.l14.jdbc.Entity;

import ru.otus.l14.jdbc.Id;

public class Account {

    @Id
    private Long no;

    private String type;

    private Number rest;

    public Account(String type, Long rest) {
        this.type = type;
        this.rest = rest;
    }

    public Account(Long no, String type, Long rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public Account() {
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public Long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getRest() {
        return rest;
    }

    public void setRest(Number rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" + "no=" + no + ", type='" + type + '\'' + ", rest=" + rest + '}';
    }
}
