package ru.otus.l11.atm;

import ru.otus.l11.atm.model.Nominal;

import java.util.HashMap;
import java.util.Map;

import static ru.otus.l11.atm.model.Nominal.*;

public class AtmImpl implements Atm {

    private Map<Nominal, Integer> totalCashAmount = new HashMap<>();
    private AtmState atmState;

    private AtmImpl() {
        this.totalCashAmount.put(_1000, 0);
        this.totalCashAmount.put(_500, 0);
        this.totalCashAmount.put(_100, 0);
        this.totalCashAmount.put(_50, 0);
        this.totalCashAmount.put(_10, 0);
    }

    /**
     * @param nominal - номинал банкноты
     * @param amount - количество банкнот
     */
    @Override
    public void depositCash(Nominal nominal, Integer amount) {
        this.totalCashAmount.put(nominal, this.totalCashAmount.get(nominal) + amount);
    }

    @Override
    public Long checkBalance() {
        return this.totalCashAmount.entrySet().stream().mapToLong(entry -> entry.getValue() * Integer.valueOf(entry.getKey().toString().substring(1))).sum();
    }

    @Override
    public Map<Nominal, Integer> cashGiveOut(Integer cashToGiveOut) {
        if (cashToGiveOut > checkBalance())
            throw new RuntimeException("В банкомате отсутствует запрошенная сумма");

        int[] nominalValues = {1000, 500, 100, 50, 10};
        Map<Nominal, Integer> releasedCash = new HashMap<>();
        releasedCash.put(_1000, 0);
        releasedCash.put(_500, 0);
        releasedCash.put(_100, 0);
        releasedCash.put(_50, 0);
        releasedCash.put(_10, 0);

        for (int i = 0; i < this.totalCashAmount.size() && cashToGiveOut != 0; i++) {
            Nominal nominal = parse(nominalValues[i]);
            if (cashToGiveOut >= nominalValues[i] && this.totalCashAmount.get(nominal) * nominalValues[i] >= cashToGiveOut) {
                int releasedCounter = cashToGiveOut / nominalValues[i];
                releasedCash.put(nominal, releasedCounter);
                cashToGiveOut = cashToGiveOut % nominalValues[i];
                this.totalCashAmount.put(nominal, this.totalCashAmount.get(nominal) - releasedCounter);
            } else if (cashToGiveOut >= nominalValues[i]) {
                releasedCash.put(nominal, this.totalCashAmount.get(nominal));
                cashToGiveOut = cashToGiveOut - this.totalCashAmount.get(nominal) * nominalValues[i];
                this.totalCashAmount.put(nominal, 0);
            }
        }
        if (cashToGiveOut != 0) {
            releasedCash.forEach((key, value) -> this.totalCashAmount.put(key, this.totalCashAmount.get(key) + value));
            throw new RuntimeException("Запрошенную сумму невозможно выдать");
        } else
            return releasedCash;
    }

    @Override
    public void rollbackToDefaultState() {
        this.totalCashAmount.clear();
        this.totalCashAmount.putAll(this.atmState.amtCashState);
    }

    @Override
    public void initDefaultState() {
        this.atmState = new AtmState(this.totalCashAmount);
    }

    /**
     * Состояние банкомата
     */
    private class AtmState {

        private final Map<Nominal, Integer> amtCashState = new HashMap<>();

        private AtmState(Map<Nominal, Integer> totalCashAmount) {
            this.amtCashState.putAll(totalCashAmount);
        }
    }

    /**
     * Фабрика банкоматов
     */
    public enum AtmFactory {

        INSTANCE;

        public Atm getAtmInstance() {
            return new AtmImpl();
        }
    }
}
