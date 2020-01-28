package ru.otus.l18.dao;

import ru.otus.l18.model.User;

import java.util.List;

public interface UserDao {

    void create(User object);

    void update(User object);

    User load(Long id);

    List<User> loadAll();
}
