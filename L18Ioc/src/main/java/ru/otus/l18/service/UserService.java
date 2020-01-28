package ru.otus.l18.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.l18.dao.UserDao;
import ru.otus.l18.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
       return userDao.loadAll();
    }

    public void saveUser(User user) {
        userDao.create(user);
    }

    public List<String> validateUser(User user) {
        List<String> errors = new ArrayList<>();
        if (isBlank(user.getName()))
            errors.add("Имя");
        if (user.getAge() < 1)
            errors.add("Возраст");
        if (user.getAddressDataSet() == null || isBlank(user.getAddressDataSet().getStreet()))
            errors.add("Адрес");
        if (user.getPhoneDataSet() == null || user.getPhoneDataSet().isEmpty())
            errors.add("Телефон");
        return errors;
    }
}
