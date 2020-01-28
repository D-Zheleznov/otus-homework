package ru.otus.l17.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.File;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBManager {

    public static MongoDatabase getConnection() {
        try {
            Configuration config = new Configurations().properties(new File("application.properties"));
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
            MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();
            MongoClient client = MongoClients.create(settings);
            MongoDatabase database = client.getDatabase(config.getString("database.db"));
            createCredentials(database, config);
            return database;
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Ошибка подключения к базе данных");
    }

    public static void dropCollection(String collectionName) {
        getConnection().getCollection(collectionName).drop();
    }

    private static void createCredentials(MongoDatabase database, Configuration config) {
        MongoCollection<Document> dbCredentials = database.getCollection("credentials");
        if (dbCredentials.find(Filters.and(Filters.eq("login", config.getString("database.login")),
                                           Filters.eq("password", config.getString("database.password")))).first() == null) {
            dbCredentials.insertOne(new Document()
                    .append("login", config.getString("database.login"))
                    .append("password", config.getString("database.password"))
                    .append("active", false));
        }
    }

    public static boolean checkAuthIsActive() {
        Document credentials = getConnection().getCollection("credentials").find().first();
        return credentials != null && !credentials.get("active").equals(false);
    }

    public static boolean checkAuth(String login, String password) {
        return getConnection().getCollection("credentials").find(Filters.and(Filters.eq("login", login),
                                                                                Filters.eq("password", password))).first() != null;
    }

    public static void dropAuth() {
        MongoCollection<Document> credentialsCollection = getConnection().getCollection("credentials");
        credentialsCollection.updateOne(credentialsCollection.find().first(), Updates.set("active", false));
    }

    public static void activateAuth() {
        MongoCollection<Document> credentialsCollection = getConnection().getCollection("credentials");
        credentialsCollection.updateOne(credentialsCollection.find().first(), Updates.set("active", true));
    }
}
