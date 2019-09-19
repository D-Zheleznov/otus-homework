package ru.otus.l10.atm;

import ru.otus.l10.atm.model.CashPack;
import ru.otus.l10.atm.model.Nominal;

import java.util.*;

public class AtmImpl implements Atm {

    private static final Atm atm = new AtmImpl();
    private final Map<Nominal, Integer> totalCashAmount = new HashMap<>();

    private AtmImpl() {
        totalCashAmount.put(Nominal._1000, 0);
        totalCashAmount.put(Nominal._500, 0);
        totalCashAmount.put(Nominal._100, 0);
        totalCashAmount.put(Nominal._50, 0);
        totalCashAmount.put(Nominal._10, 0);
    }

    public static Atm getAtmInstance() {
        return atm;
    }

    public void depositCash(List<CashPack> cashToDeposit) {
        cashToDeposit.forEach(cash -> totalCashAmount.put(cash.getNominal(), totalCashAmount.get(cash.getNominal()) + cash.getAmount()));
    }

    public List<CashPack> cashGiveOut(Integer cashToGiveOut) {
        if (cashToGiveOut > checkBalance())
            throw new RuntimeException("В банкомате отсутствует запрошенная сумма");

        List<CashPack> releasedCash = new ArrayList<>();
        int[] nominalValues = {1000, 500, 100, 50, 10};

        for (int i = 0; i < totalCashAmount.size() && cashToGiveOut != 0; i++) {
            if (cashToGiveOut >= nominalValues[i] && totalCashAmount.get(Nominal.parse(nominalValues[i])) * nominalValues[i] >= cashToGiveOut) {
                int releasedCounter = cashToGiveOut / nominalValues[i];
                releasedCash.add(new CashPack(Nominal.parse(nominalValues[i]), releasedCounter));
                cashToGiveOut = cashToGiveOut % nominalValues[i];
                totalCashAmount.put(Nominal.parse(nominalValues[i]), totalCashAmount.get(Nominal.parse(nominalValues[i])) - releasedCounter);
            } else if (cashToGiveOut >= nominalValues[i]) {
                releasedCash.add(new CashPack(Nominal.parse(nominalValues[i]), totalCashAmount.get(Nominal.parse(nominalValues[i]))));
                cashToGiveOut = cashToGiveOut - totalCashAmount.get(Nominal.parse(nominalValues[i])) * nominalValues[i];
                totalCashAmount.put(Nominal.parse(nominalValues[i]), 0);
            }
        }
        if (cashToGiveOut != 0) {
            releasedCash.forEach(cash -> totalCashAmount.put(cash.getNominal(), totalCashAmount.get(cash.getNominal()) + cash.getAmount()));
            throw new RuntimeException("Запрошенную сумму невозможно выдать");
        } else
            return releasedCash;
    }

    public Integer checkBalance() {
        return totalCashAmount.entrySet().stream().mapToInt(entry -> entry.getValue() * Integer.valueOf(entry.getKey().toString().substring(1))).sum();
    }
}
