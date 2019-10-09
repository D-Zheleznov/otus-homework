package ru.otus.l14.jdbc;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class DbExecutorImpl<T> implements DbExecutor<T> {

    @Override
    @SuppressWarnings({"unchecked"})
    public void create(T objectData) {
        this.getPrimaryKey((Class<T>) objectData.getClass());
        Set<Field> fields = Arrays.stream(objectData.getClass().getDeclaredFields()).filter(field -> Arrays.stream(field.getDeclaredAnnotations()).noneMatch(annotation -> annotation.annotationType().equals(Id.class))).collect(toSet());

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(objectData.getClass().getSimpleName()).append(" (").append(fields.stream().map(Field::getName).collect(joining(", "))).append(") VALUES ('");
        query.append(fields.stream().map(field -> {
            try {
                field.setAccessible(true);
                return field.get(objectData).toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(joining("', '"))).append("')");
        executeStatement(query.toString());
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void update(T objectData) {
        Field primaryKey = this.getPrimaryKey((Class<T>) objectData.getClass());
        primaryKey.setAccessible(true);
        Set<Field> fields = Arrays.stream(objectData.getClass().getDeclaredFields()).filter(field -> !field.getName().equals(primaryKey.getName())).collect(toSet());

        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(objectData.getClass().getSimpleName()).append(" SET ");
        fields.forEach(field -> {
            try {
                field.setAccessible(true);
                query.append(field.getName()).append(" = '").append(field.get(objectData)).append("', ");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        try {
            query.setLength(query.length() - 2);
            query.append(" WHERE ").append(primaryKey.getName()).append(" = '").append(primaryKey.get(objectData)).append("'");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        executeStatement(query.toString());
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void createOrUpdate(T objectData) {
        Field primaryKey = this.getPrimaryKey((Class<T>) objectData.getClass());
        primaryKey.setAccessible(true);
        try {
            if (primaryKey.get(objectData) != null)
                this.update(objectData);
            else
                this.create(objectData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T load(long id, Class<T> clazz) {
        Field primaryKey = this.getPrimaryKey(clazz);
        primaryKey.setAccessible(true);
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(clazz.getSimpleName()).append(" WHERE ").append(primaryKey.getName()).append(" = '").append(id).append("'");
        return getObjectFromQuery(query.toString(), clazz);
    }

    public static void executeStatement(String sqlQuery) {
        try {
            Configuration config = new Configurations().properties(new File("application.properties"));

            try (Connection connection = DriverManager.getConnection(config.getString("database.url"), config.getString("database.username"), config.getString("database.password"));
                 Statement statement = connection.createStatement()) {
                Class.forName(config.getString("database.jdbc.driver"));

                statement.execute(sqlQuery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private T getObjectFromQuery(String sqlQuery, Class<T> clazz) {
        try {
            Configuration config = new Configurations().properties(new File("application.properties"));

            try (Connection connection = DriverManager.getConnection(config.getString("database.url"), config.getString("database.username"), config.getString("database.password"));
                 Statement statement = connection.createStatement()) {
                Class.forName(config.getString("database.jdbc.driver"));

                try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                    T object = clazz.getDeclaredConstructor().newInstance();
                    Set<Method> setters = Arrays.stream(clazz.getMethods()).filter(method -> method.getName().startsWith("set")).collect(toSet());
                    if (resultSet.next()) {
                        for (Method setter : setters) {
                            setter.invoke(object, resultSet.getObject(setter.getName().substring(3).toUpperCase()));
                        }
                    }
                    return object;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Field getPrimaryKey(Class<T> clazz) {
        Set<Field> primaryKeys = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Arrays.stream(field.getDeclaredAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().equals(Id.class)))
                .collect(toSet());
        if (primaryKeys.size() != 1)
            throw new RuntimeException("В классе " + clazz.getSimpleName() + " должно быть одно поле помеченное ключом @Id");
        else
            return primaryKeys.stream().findFirst().get();
    }
}
