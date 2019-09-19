package ru.otus.l10.atm;

import ru.otus.l10.atm.model.CashPack;

import java.util.List;

public interface Atm {

    void depositCash(List<CashPack> cashToDeposit);

    List<CashPack> cashGiveOut(Integer cashToGiveOut);

    Integer checkBalance();
}
