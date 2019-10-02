import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l14.jdbc.DbExecutor;
import ru.otus.l14.jdbc.DbExecutorImpl;
import ru.otus.l14.jdbc.Entity.Account;
import ru.otus.l14.jdbc.Entity.User;

import static org.junit.Assert.*;

public class JdbcTester {

    @Before
    public void before() {
        String userTableSql = "CREATE TABLE IF NOT EXISTS User (id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))";
        String accountTableSql = "CREATE TABLE IF NOT EXISTS Account (no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)";
        DbExecutorImpl.executeStatement(userTableSql);
        DbExecutorImpl.executeStatement(accountTableSql);
    }

    @Test
    public void test() {
        DbExecutor<User> userExecutor = new DbExecutorImpl<>();

        System.out.println("Создаем нового пользователя в базе");
        userExecutor.create(new User("Denis", 26));

        System.out.println("Проверяем что пользователь был создан запросив его по ID = 1:");
        User user = userExecutor.load(1L, User.class);
        assertNotNull(user);
        System.out.println("Пользователь в базе с ID = 1: " + user);

        System.out.println("Поменяем возраст пользователя на 27");
        userExecutor.update(new User(1L, "Denis", 27));

        System.out.println("Проверяем что возраст пользователя поменялся:");
        user = userExecutor.load(1L, User.class);
        assertNotNull(user);
        assertEquals(27L, (long) user.getAge());
        System.out.println("Пользователь в базе с ID = 1: " + user);

        System.out.println("///////////////////////////////////////////////////////////////");
        DbExecutor<Account> accountExecutor = new DbExecutorImpl<>();

        System.out.println("Создаем новый счет в базе");
        accountExecutor.createOrUpdate(new Account("TestAccount", 1000L));

        System.out.println("Проверяем что счет был создан запросив его по NO = 1:");
        Account account = accountExecutor.load(1L, Account.class);
        assertNotNull(account);
        System.out.println("Счет в базе с NO = 1: " + account);

        System.out.println("Поменяем название счета на ProdAccount");
        accountExecutor.createOrUpdate(new Account(1L, "ProdAccount", 1000L));

        System.out.println("Проверяем что счет был создан запросив его по NO = 1:");
        account = accountExecutor.load(1L, Account.class);
        assertNotNull(account);
        assertEquals("ProdAccount", account.getType());
        System.out.println("Счет в базе с NO = 1: " + account);
    }

    @After
    public void after() {
        String dropUsersSql = "DROP TABLE User";
        String dropAccountsSql = "DROP TABLE Account";
        DbExecutorImpl.executeStatement(dropUsersSql);
        DbExecutorImpl.executeStatement(dropAccountsSql);
    }
}
