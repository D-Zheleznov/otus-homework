package ru.otus.l11.department;

import ru.otus.l11.atm.Atm;

import java.util.ArrayList;
import java.util.List;

public class DepartmentImpl implements Department {

    private List<Atm> atms = new ArrayList<>();

    @Override
    public Long checkAtmsBalanceSum() {
        return this.atms.stream().mapToLong(Atm::checkBalance).sum();
    }

    @Override
    public void initAtmsDefaultState() {
        atms.forEach(Atm::rollbackToDefaultState);
    }

    public List<Atm> getAtms() {
        return atms;
    }
}
