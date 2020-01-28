package ru.otus.l17.service;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import ru.otus.l17.service.entity.User;

import java.util.*;
import java.util.function.Consumer;

public class DBUserServiceImpl implements DBUserService {

    private MongoCollection<User> collection;

    public static DBUserServiceImpl init(MongoDatabase database) {
        DBUserServiceImpl dbService = new DBUserServiceImpl();
        dbService.collection = database.getCollection("users", User.class);
        return dbService;
    }

    private DBUserServiceImpl() {
    }

    @Override
    public void create(User user) {
        user.setId(new Random().nextLong());
        this.collection.insertOne(user);
    }

    @Override
    public void update(User user) {
        this.collection.findOneAndReplace(Filters.eq("_id", user.getId()), user);
    }

    @Override
    public User load(Long id) {
        return this.collection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public List<User> loadAll() {
        List<User> allData = new ArrayList<>();
        this.collection.find().forEach((Consumer<? super User>) allData::add);
        return allData;
    }
}
