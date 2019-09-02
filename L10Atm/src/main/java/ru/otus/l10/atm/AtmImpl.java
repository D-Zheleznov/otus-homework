package ru.otus.l10.atm;

import ru.otus.l10.atm.model.Cash;
import ru.otus.l10.atm.model.Nominal;

import java.util.*;

public class AtmImpl implements Atm {

    private static final Atm ATM = new AtmImpl();
    private final Map<Nominal, Integer> ATM_CASH_AMOUNT = new HashMap<>();

    private AtmImpl() {
        ATM_CASH_AMOUNT.put(Nominal._1000, 0);
        ATM_CASH_AMOUNT.put(Nominal._500, 0);
        ATM_CASH_AMOUNT.put(Nominal._100, 0);
        ATM_CASH_AMOUNT.put(Nominal._50, 0);
        ATM_CASH_AMOUNT.put(Nominal._10, 0);
    }

    public static Atm getAtmInstance() {
        return ATM;
    }

    public void depositCash(List<Cash> cashToDeposit) {
        cashToDeposit.forEach(cash -> ATM_CASH_AMOUNT.put(cash.getNominal(), ATM_CASH_AMOUNT.get(cash.getNominal()) + cash.getAmount()));
    }

    public List<Cash> cashGiveOut(Integer cashToGiveOut) {
        if (cashToGiveOut > checkBalance())
            throw new RuntimeException("В банкомате отсутствует запрошенная сумма");

        List<Cash> releasedCash = new ArrayList<>();
        int[] nominalValues = {1000, 500, 100, 50, 10};

        for (int i = 0; i < ATM_CASH_AMOUNT.size() && cashToGiveOut != 0; i++) {
            if (cashToGiveOut >= nominalValues[i] && ATM_CASH_AMOUNT.get(Nominal.parse(nominalValues[i])) * nominalValues[i] >= cashToGiveOut) {
                int releasedCounter = cashToGiveOut / nominalValues[i];
                releasedCash.add(new Cash(Nominal.parse(nominalValues[i]), releasedCounter));
                cashToGiveOut = cashToGiveOut % nominalValues[i];
                ATM_CASH_AMOUNT.put(Nominal.parse(nominalValues[i]), ATM_CASH_AMOUNT.get(Nominal.parse(nominalValues[i])) - releasedCounter);
            } else if (cashToGiveOut >= nominalValues[i]) {
                releasedCash.add(new Cash(Nominal.parse(nominalValues[i]), ATM_CASH_AMOUNT.get(Nominal.parse(nominalValues[i]))));
                cashToGiveOut = cashToGiveOut - ATM_CASH_AMOUNT.get(Nominal.parse(nominalValues[i])) * nominalValues[i];
                ATM_CASH_AMOUNT.put(Nominal.parse(nominalValues[i]), 0);
            }
        }
        if (cashToGiveOut != 0)
            throw new RuntimeException("Запрошенную сумму невозможно выдать");
        else
            return releasedCash;
    }

    public Integer checkBalance() {
        return ATM_CASH_AMOUNT.entrySet().stream().mapToInt(entry -> entry.getValue() * Integer.valueOf(entry.getKey().toString().substring(1))).sum();
    }
}
