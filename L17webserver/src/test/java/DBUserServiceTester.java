import org.junit.After;
import org.junit.Test;
import ru.otus.l17.service.DBManager;
import ru.otus.l17.service.DBUserService;
import ru.otus.l17.service.DBUserServiceImpl;
import ru.otus.l17.service.entity.AddressDataSet;
import ru.otus.l17.service.entity.PhoneDataSet;
import ru.otus.l17.service.entity.User;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DBUserServiceTester {

    private DBUserService dbUserService = DBUserServiceImpl.init(DBManager.getConnection());

    @Test
    public void test() {
        User denis = new User("Denis", 26);
        AddressDataSet address = new AddressDataSet("Нагатинская ул.");
        PhoneDataSet phone = new PhoneDataSet("88005553535");
        denis.setAddressDataSet(address);
        denis.setPhoneDataSet(Collections.singletonList(phone));

        System.out.println("Создаем нового пользователя в базе");
        dbUserService.create(denis);
        assertNotNull(denis.getId());

        System.out.println("Проверяем что пользователь был создан запросив его по ID " + denis.getId());
        User createdUser1 = dbUserService.load(denis.getId());
        assertNotNull(createdUser1);
        System.out.println("Пользователь в базе " + createdUser1);

        System.out.println("Поменяем возраст пользователя, улицу и номер телефона");
        createdUser1.setAge(27);
        createdUser1.getAddressDataSet().setStreet("Арбатская ул.");
        createdUser1.getPhoneDataSet().stream().findFirst().get().setNumber("88009999999");
        dbUserService.update(createdUser1);

        System.out.println("Проверяем что данные пользователя поменялись:");
        createdUser1 = dbUserService.load(denis.getId());
        assertNotNull(createdUser1);
        assertEquals(27L, (long) createdUser1.getAge());
        System.out.println("Пользователь в базе " + createdUser1);

        System.out.println("Получаем всё содержимое коллекции");
        List<User> users = dbUserService.loadAll();
        assertEquals(1, users.size());
        System.out.println(users);
    }

    @After
    public void after() {
        DBManager.dropCollection("users");
    }
}
