package ru.otus.l10.atm.model;

public class Cash {

    private Nominal nominal;
    private Integer amount;

    public Cash(Nominal nominal, Integer amount) {
        this.nominal = nominal;
        this.amount = amount;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setNominal(Nominal nominal) {
        this.nominal = nominal;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "(Номинал: " + nominal + ", кол-во: " + amount + ")";
    }
}
