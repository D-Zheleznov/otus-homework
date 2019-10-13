package ru.otus.l14.jdbc;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import ru.otus.l14.jdbc.annotations.Id;
import ru.otus.l14.jdbc.exceptions.DbExecutorException;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

public class SqlManager {

    public static Connection getConnection() throws DbExecutorException {
        try {
            Configuration config = new Configurations().properties(new File("application.properties"));
            Connection connection = DriverManager.getConnection(config.getString("database.url"),
                                                                config.getString("database.username"),
                                                                config.getString("database.password"));
            Class.forName(config.getString("database.jdbc.driver"));
            return connection;
        } catch (Exception e) {
            throw new DbExecutorException(e.getMessage());
        }
    }

    public static String generateQuery(QueryType queryType, String tableName, List<Field> fields) {
        Field primaryKey = fields.stream().filter(field -> Arrays.stream(field.getDeclaredAnnotations())
                                          .anyMatch(annotation -> annotation.annotationType().equals(Id.class)))
                                          .findFirst().get();

        List<Field> fieldsWithoutId = fields.stream().filter(field -> !field.getName().equals(primaryKey.getName())).collect(toList());

        StringBuilder query = new StringBuilder();
        switch (queryType) {
            case SELECT: {
                query.append("SELECT ").append(fields.stream().map(Field::getName).collect(joining(", ")))
                     .append(" FROM ").append(tableName).append(" WHERE ").append(primaryKey.getName()).append(" = ?");
                return query.toString();
            }
            case INSERT: {
                query.append("INSERT INTO ").append(tableName)
                     .append(" (").append(fieldsWithoutId.stream().map(Field::getName).collect(joining(", ")))
                     .append(") VALUES (");

                for (int i = 0; i < fieldsWithoutId.size(); i++)
                    query.append("?, ");

                query.append(")");
                return query.toString();
            }
            case UPDATE: {
                query.append("UPDATE ").append(tableName).append(" SET ");
                query.append(fieldsWithoutId.stream().map(Field::getName).collect(joining(" = ?, "))).append(" = ?");
                query.append(" WHERE ").append(primaryKey.getName()).append(" = ?");
                return query.toString();
            }
            default: return null;
        }
    }

    public enum QueryType {
        SELECT, INSERT, UPDATE
    }
}
