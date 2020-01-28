package ru.otus.l18.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
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
            return client.getDatabase(config.getString("database.db"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Ошибка подключения к базе данных");
    }
}
