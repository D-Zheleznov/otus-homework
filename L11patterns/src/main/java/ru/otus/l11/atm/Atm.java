package ru.otus.l11.atm;

import ru.otus.l11.atm.model.Nominal;

import java.util.Map;

public interface Atm {

    void depositCash(Nominal nominal, Integer amount);

    Map<Nominal, Integer> cashGiveOut(Integer cashToGiveOut);

    Long checkBalance();

    void initDefaultState();

    void rollbackToDefaultState();
}
