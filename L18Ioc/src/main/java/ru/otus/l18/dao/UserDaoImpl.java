package ru.otus.l18.dao;

import com.mongodb.client.model.Filters;
import org.springframework.stereotype.Component;
import ru.otus.l18.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Component
public class UserDaoImpl implements UserDao {

    private UserDaoImpl() {
    }

    @Override
    public void create(User user) {
        user.setId(new Random().nextLong());
        DBManager.getConnection().getCollection("users", User.class).insertOne(user);
    }

    @Override
    public void update(User user) {
        DBManager.getConnection().getCollection("users", User.class).findOneAndReplace(Filters.eq("_id", user.getId()), user);
    }

    @Override
    public User load(Long id) {
        return DBManager.getConnection().getCollection("users", User.class).find(Filters.eq("_id", id)).first();
    }

    @Override
    public List<User> loadAll() {
        List<User> allData = new ArrayList<>();
        DBManager.getConnection().getCollection("users", User.class).find().forEach((Consumer<? super User>) allData::add);
        return allData;
    }
}
