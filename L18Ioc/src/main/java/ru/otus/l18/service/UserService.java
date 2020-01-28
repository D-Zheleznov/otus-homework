package ru.otus.l18.service;

import org.springframework.stereotype.Service;
import ru.otus.l18.dao.UserDao;
import ru.otus.l18.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
       return userDao.loadAll();
    }

    public void saveUser(User user) {
        userDao.create(user);
    }

    public List<String> validateUser(User user) {
        List<String> errors = new ArrayList<>();
        if (user.getName() == null || user.getName().length() < 1)
            errors.add("Укажите Имя");
        if (user.getAge() == null || user.getAge() < 1)
            errors.add("Укажите Возраст");
        if (user.getAddressDataSet() == null || user.getAddressDataSet().getStreet().length() < 1)
            errors.add("Укажите Адрес");
        if (user.getPhoneDataSet() == null || user.getPhoneDataSet().isEmpty())
            errors.add("Укажите Телефон");
        return errors;
    }
}
