package ru.otus.l10.atm.model;

public enum Nominal {

    _10,
    _50,
    _100,
    _500,
    _1000;

    public static Nominal parse(Integer sum) {
        switch (sum) {
            case 10: return _10;
            case 50: return _50;
            case 100: return _100;
            case 500: return _500;
            case 1000: return _1000;
            default: throw new RuntimeException("Номинал не распознан: " + sum);
        }
    }
}
