import org.junit.Test;
import ru.otus.l10.atm.Atm;
import ru.otus.l10.atm.AtmImpl;
import ru.otus.l10.atm.model.CashPack;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static ru.otus.l10.atm.model.Nominal.*;

public class AtmTester {

    private static Atm atm = AtmImpl.getAtmInstance();

    @Test
    public void testValidCashAmountGiveOut() {
        List<CashPack> cashToDeposit = new ArrayList<>();
        cashToDeposit.add(new CashPack(_500, 3));
        cashToDeposit.add(new CashPack(_100, 3));
        cashToDeposit.add(new CashPack(_1000, 3));
        cashToDeposit.add(new CashPack(_50, 3));
        cashToDeposit.add(new CashPack(_10, 5));
        atm.depositCash(cashToDeposit);

        assertEquals(5000L, (long) atm.checkBalance());
        System.out.println("Остаток на счёте: " + atm.checkBalance());
        System.out.println("Выдано банкнотами: " + atm.cashGiveOut(4240));
        assertEquals(760L, (long) atm.checkBalance());
        System.out.println("Остаток на счёте: " + atm.checkBalance());
    }

    /**
     * Кейс когда запрошенную сумму невозможно выдать
     */
    @Test
    public void testInvalidCashAmountGiveOut() {
        Integer balance = atm.checkBalance();
        if (balance == 0) {
            List<CashPack> cashToDeposit = new ArrayList<>();
            cashToDeposit.add(new CashPack(_500, 1));
            cashToDeposit.add(new CashPack(_100, 2));
            cashToDeposit.add(new CashPack(_50, 1));
            cashToDeposit.add(new CashPack(_10, 1));
            atm.depositCash(cashToDeposit);
        }
        System.out.println("Остаток на счёте: " + atm.checkBalance());
        try {
            System.out.println("Выдано банкнотами: " + atm.cashGiveOut(753));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            assertEquals(760L, (long) atm.checkBalance());
            System.out.println("Остаток на счёте: " + atm.checkBalance());
        }
    }

    /**
     * Кейс когда запрошенная сумма превышает остаток
     */
    @Test
    public void testInvalidCashAmountGiveOut2() {
        Integer balance = atm.checkBalance();
        if (balance == 0) {
            List<CashPack> cashToDeposit = new ArrayList<>();
            cashToDeposit.add(new CashPack(_500, 1));
            cashToDeposit.add(new CashPack(_100, 2));
            cashToDeposit.add(new CashPack(_50, 1));
            cashToDeposit.add(new CashPack(_10, 1));
            atm.depositCash(cashToDeposit);
        }
        System.out.println("Остаток на счёте: " + atm.checkBalance());
        try {
            System.out.println("Выдано банкнотами: " + atm.cashGiveOut(800));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            assertEquals(760L, (long) atm.checkBalance());
            System.out.println("Остаток на счёте: " + atm.checkBalance());
        }
    }
}
