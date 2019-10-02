import org.junit.Test;
import ru.otus.l11.atm.Atm;
import ru.otus.l11.atm.AtmImpl;
import ru.otus.l11.department.DepartmentImpl;

import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static ru.otus.l11.atm.model.Nominal.*;

public class PatternsTester {

    /**
     * Тест логики департамента
     */
    @Test
    public void testDepartmentLogic() {
        DepartmentImpl department = new DepartmentImpl();
        System.out.println("Создание департамента с 3 ATM");
        System.out.println("-----------------------------");

        Atm atm1 = AtmImpl.AtmFactory.INSTANCE.getAtmInstance();
        atm1.depositCash(_10, 5);
        atm1.depositCash(_50, 3);
        atm1.depositCash(_100, 3);
        atm1.depositCash(_500, 3);
        atm1.depositCash(_1000, 3);
        assertEquals(5000L, atm1.checkBalance().longValue());

        Atm atm2 = AtmImpl.AtmFactory.INSTANCE.getAtmInstance();
        atm2.depositCash(_10, 5);
        atm2.depositCash(_50, 3);
        atm2.depositCash(_100, 3);
        atm2.depositCash(_500, 3);
        atm2.depositCash(_1000, 5);
        assertEquals(7000L, atm2.checkBalance().longValue());

        Atm atm3 = AtmImpl.AtmFactory.INSTANCE.getAtmInstance();
        atm3.depositCash(_10, 5);
        atm3.depositCash(_50, 3);
        atm3.depositCash(_100, 3);
        atm3.depositCash(_500, 5);
        atm3.depositCash(_1000, 5);
        assertEquals(8000L, atm3.checkBalance().longValue());

        department.getAtms().add(atm1);
        department.getAtms().add(atm2);
        department.getAtms().add(atm3);
        assertEquals(3, department.getAtms().size());

        System.out.println("Остатки на счетах банкоматов: " + department.getAtms().stream().map(atm -> atm.checkBalance().toString()).collect(Collectors.joining(", ")));
        assertEquals(20000L, department.checkAtmsBalanceSum().longValue());
        System.out.println("Сумма остатков со всех АТМ: " + department.checkAtmsBalanceSum());

        department.getAtms().forEach(Atm::initDefaultState);
        System.out.println("Начальные состояния ATM сохранены\n");

        department.getAtms().forEach(atm -> atm.depositCash(_1000, 1));
        System.out.println("Добавили во все банкоматы по 1 банкноте номиналом 1000");
        System.out.println("Остатки на счетах банкоматов: " + department.getAtms().stream().map(atm -> atm.checkBalance().toString()).collect(Collectors.joining(", ")));
        assertEquals(23000L, department.checkAtmsBalanceSum().longValue());
        System.out.println("Сумма остатков со всех АТМ: " + department.checkAtmsBalanceSum() + "\n");

        department.initAtmsDefaultState();
        assertEquals(5000L, atm1.checkBalance().longValue());
        assertEquals(7000L, atm2.checkBalance().longValue());
        assertEquals(8000L, atm3.checkBalance().longValue());
        System.out.println("Откатили состояние банкоматов до начальных состояний");
        System.out.println("Остатки на счетах банкоматов: " + department.getAtms().stream().map(atm -> atm.checkBalance().toString()).collect(Collectors.joining(", ")));
        assertEquals(20000L, department.checkAtmsBalanceSum().longValue());
        System.out.println("Сумма остатков со всех АТМ: " + department.checkAtmsBalanceSum());
    }

    /**
     * Тест состояний банкомата
     */
    @Test
    public void testAtmState() {
        Atm atm1 = AtmImpl.AtmFactory.INSTANCE.getAtmInstance();
        atm1.depositCash(_10, 5);
        atm1.depositCash(_50, 3);
        atm1.depositCash(_100, 3);
        atm1.depositCash(_500, 3);
        atm1.depositCash(_1000, 3);
        atm1.initDefaultState();

        assertEquals(5000L, atm1.checkBalance().longValue());
        System.out.println("Остаток на счёте АТМ1: " + atm1.checkBalance());

        atm1.depositCash(_1000, 3);
        assertEquals(8000L, atm1.checkBalance().longValue());
        System.out.println("Остаток на счёте АТМ1: " + atm1.checkBalance());

        atm1.rollbackToDefaultState();
        assertEquals(5000L, atm1.checkBalance().longValue());
        System.out.println("Остаток на счёте АТМ1: " + atm1.checkBalance());
    }

    /**
     * Тест логики банкомата
     */
    @Test
    public void testAtmLogic() {
        Atm atm1 = AtmImpl.AtmFactory.INSTANCE.getAtmInstance();
        atm1.depositCash(_10, 5);
        atm1.depositCash(_50, 3);
        atm1.depositCash(_100, 3);
        atm1.depositCash(_500, 3);
        atm1.depositCash(_1000, 3);

        assertEquals(5000L, atm1.checkBalance().longValue());
        System.out.println("Остаток на счёте: " + atm1.checkBalance());
        System.out.println("Выдано банкнотами: " + atm1.cashGiveOut(4240));
        assertEquals(760L, atm1.checkBalance().longValue());
        System.out.println("Остаток на счёте: " + atm1.checkBalance());
    }
}
