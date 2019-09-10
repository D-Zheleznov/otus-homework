package ru.otus.l10.atm.model;

/**
 * Класс "Пачка банкнот"
 * nominal - номинал банкнот
 * amount - количество банкнот в пачке
 */
public class CashPack {

    private Nominal nominal;
    private Integer amount;

    public CashPack(Nominal nominal, Integer amount) {
        this.nominal = nominal;

        if (amount < 0)
            throw new RuntimeException("Количество банкнот не может быть отрицательным");
        this.amount = amount;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "(Номинал: " + nominal + ", кол-во: " + amount + ")";
    }
}
