import org.hibernate.Session;
import org.junit.After;
import org.junit.Test;
import ru.otus.l15.hibernate.HibernateManager;
import ru.otus.l15.hibernate.HibernateManagerImpl;
import ru.otus.l15.hibernate.entity.AddressDataSet;
import ru.otus.l15.hibernate.entity.PhoneDataSet;
import ru.otus.l15.hibernate.entity.User;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateTester {

    private static HibernateManager hibernateManager = HibernateManagerImpl.of("hibernate.cfg.xml", User.class, PhoneDataSet.class, AddressDataSet.class);

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        User denis = new User("Denis", 26);
        AddressDataSet address = new AddressDataSet("Нагатинская ул.");
        PhoneDataSet phone = new PhoneDataSet("88005553535");
        denis.setAddressDataSet(address);
        denis.setPhoneDataSet(Collections.singletonList(phone));

        System.out.println("Создаем нового пользователя в базе");
        hibernateManager.create(denis);
        assertNotNull(denis.getId());

        System.out.println("Проверяем что пользователь был создан запросив его по ID = 1:");
        User createdUser1 = (User) hibernateManager.load(1L, User.class);
        assertNotNull(createdUser1);
        System.out.println("Пользователь в базе с ID = 1: " + createdUser1);

        System.out.println("Поменяем возраст пользователя, улицу и номер телефона");
        User denis2 = new User("Denis", 27);
        denis2.setId(1L);
        AddressDataSet address2 = new AddressDataSet("Арбатская ул.");
        address2.setId(1L);
        PhoneDataSet phone2 = new PhoneDataSet("88009999999");
        phone2.setId(1L);
        denis2.setAddressDataSet(address2);
        denis2.setPhoneDataSet(Collections.singletonList(phone2));
        hibernateManager.update(denis2);

        System.out.println("Проверяем что данные пользователя поменялись:");
        createdUser1 = (User) hibernateManager.load(1L, User.class);
        assertNotNull(createdUser1);
        assertEquals(27L, (long) createdUser1.getAge());
        System.out.println("Пользователь в базе с ID = 1: " + createdUser1);
    }

    @After
    public void after() {
        try (Session session = hibernateManager.getSessionFactory().openSession()) {
            session.beginTransaction();
            String dropUsersSql = "DROP TABLE USER CASCADE";
            String dropAddressSql = "DROP TABLE ADDRESS_DATA_SET";
            String dropPhoneSql = "DROP TABLE PHONE_DATA_SET";
            session.createSQLQuery(dropUsersSql).executeUpdate();
            session.createSQLQuery(dropAddressSql).executeUpdate();
            session.createSQLQuery(dropPhoneSql).executeUpdate();
            session.getTransaction().commit();
            hibernateManager.closeSessionFactory();
        }
    }
}
