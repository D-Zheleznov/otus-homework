package ru.otus.l10.main;

import ru.otus.l10.atm.Atm;
import ru.otus.l10.atm.AtmImpl;
import ru.otus.l10.atm.model.CashPack;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.l10.atm.model.Nominal.*;

public class MainClass {

    public static void main(String[] args) {
        Atm atm = AtmImpl.getAtmInstance();
        List<CashPack> cashToDeposit = new ArrayList<>();
        cashToDeposit.add(new CashPack(_500, 3));
        cashToDeposit.add(new CashPack(_100, 3));
        cashToDeposit.add(new CashPack(_1000, 3));
        cashToDeposit.add(new CashPack(_50, 3));
        cashToDeposit.add(new CashPack(_10, 5));
        atm.depositCash(cashToDeposit);

        System.out.println("Остаток на счёте: " + atm.checkBalance());
        System.out.println("Выдано банкнотами: " + atm.cashGiveOut(4240));
        System.out.println("Остаток на счёте: " + atm.checkBalance());
    }
}
