package ru.otus.l10.atm;

import ru.otus.l10.atm.model.Cash;

import java.util.List;

public interface Atm {

    void depositCash(List<Cash> cashToDeposit);

    List<Cash> cashGiveOut(Integer cashToGiveOut);

    Integer checkBalance();
}
