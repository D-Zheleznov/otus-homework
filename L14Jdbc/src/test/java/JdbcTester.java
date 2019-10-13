import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l14.jdbc.DbExecutor;
import ru.otus.l14.jdbc.DbExecutorImpl;
import ru.otus.l14.jdbc.Entity.Account;
import ru.otus.l14.jdbc.Entity.User;
import ru.otus.l14.jdbc.SqlManager;
import ru.otus.l14.jdbc.exceptions.DbExecutorException;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.*;

public class JdbcTester {

    @Before
    public void before() throws DbExecutorException {
        String userTableSql = "CREATE TABLE IF NOT EXISTS User (id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))";
        String accountTableSql = "CREATE TABLE IF NOT EXISTS Account (no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)";

        try (Connection connection = SqlManager.getConnection();
            Statement statement = connection.createStatement()) {

            statement.executeUpdate(userTableSql);
            statement.executeUpdate(accountTableSql);
        } catch (Exception e) {
            throw new DbExecutorException(e.getMessage());
        }
    }

    @Test
    public void test() throws DbExecutorException {
        DbExecutor<User> userExecutor = DbExecutorImpl.of(User.class);
        User denis = new User("Denis", 26);

        System.out.println("Создаем нового пользователя в базе");
        userExecutor.create(denis);
        assertNotNull(denis.getId());

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
        DbExecutor<Account> accountExecutor = DbExecutorImpl.of(Account.class);
        Account testAccount = new Account("TestAccount", 1000L);

        System.out.println("Создаем новый счет в базе");
        accountExecutor.createOrUpdate(testAccount);
        assertNotNull(testAccount.getNo());

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
    public void after() throws DbExecutorException {
        String dropUsersSql = "DROP TABLE User";
        String dropAccountsSql = "DROP TABLE Account";

        try (Connection connection = SqlManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(dropAccountsSql);
            statement.executeUpdate(dropUsersSql);
        } catch (Exception e) {
            throw new DbExecutorException(e.getMessage());
        }
    }
}
