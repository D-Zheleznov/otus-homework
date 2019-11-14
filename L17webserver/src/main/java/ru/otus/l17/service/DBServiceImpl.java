package ru.otus.l17.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.bson.Document;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class DBServiceImpl<T> implements DBService<T> {

    private MongoCollection<Document> collection;

    public static <T> DBServiceImpl<T> init(String collectionName) {
        DBServiceImpl<T> dbService = new DBServiceImpl<>();
        dbService.collection = getConnection().getCollection(collectionName);
        return dbService;
    }

    private DBServiceImpl() {
    }

    public static MongoDatabase getConnection() {
        try {
            Configuration config = new Configurations().properties(new File("application.properties"));
            MongoClient client = MongoClients.create(config.getString("database.url"));
            MongoDatabase database = client.getDatabase(config.getString("database.db"));
            MongoCollection<Document> dbCredentials = database.getCollection("credentials");
            dbCredentials.insertOne(new Document().append("login", config.getString("database.login")).append("password", config.getString("database.password")));
            return database;
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Ошибка подключения к базе данных");
    }

    @Override
    public void create(T objectData) {
        Document document = Document.parse(new GsonBuilder().create().toJson(objectData));
        document.append("_id", UUID.randomUUID());
        this.collection.insertOne(document);
        this.setId(objectData, document.get("_id"));
    }

    @Override
    public void update(T objectData) {
        Document updatedDocument = Document.parse(new GsonBuilder().create().toJson(objectData));
        this.collection.findOneAndReplace(Filters.eq("_id", UUID.fromString((String) updatedDocument.get("id"))), updatedDocument);
    }

    @Override
    public T load(UUID id, Class<T> clazz) {
        Document document = this.collection.find(Filters.eq("_id", id)).first();
        T objectFromDb = new Gson().fromJson(document.toJson(), clazz);
        this.setId(objectFromDb, document.get("_id"));
        return objectFromDb;
    }

    @Override
    public List<T> loadAll(Class<T> clazz) {
        List<T> allData = new ArrayList<>();
        this.collection.find().forEach((Consumer<? super Document>) document -> {
            T objectFromDb = new Gson().fromJson(document.toJson(), clazz);
            this.setId(objectFromDb, document.get("_id"));
            allData.add(objectFromDb);
        });
        return allData;
    }

    @Override
    public void dropCollection() {
        this.collection.drop();
    }

    private void setId(T objectData, Object objectId) {
        try {
            Field id = objectData.getClass().getSuperclass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(objectData, objectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
