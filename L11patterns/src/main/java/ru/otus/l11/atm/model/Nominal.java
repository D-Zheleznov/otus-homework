package ru.otus.l11.atm.model;

import java.util.Arrays;

public enum Nominal {

    _10,
    _50,
    _100,
    _500,
    _1000;

    public static Nominal parse(Integer sum) {
        return Arrays.stream(Nominal.values()).filter(nominal -> nominal.name().substring(1).equals(sum.toString())).findFirst()
                .orElseThrow(() -> new RuntimeException("Номинал не распознан: " + sum));
    }
}
