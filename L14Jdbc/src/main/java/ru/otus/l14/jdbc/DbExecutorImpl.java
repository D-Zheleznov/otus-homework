package ru.otus.l14.jdbc;

import ru.otus.l14.jdbc.annotations.Id;
import ru.otus.l14.jdbc.exceptions.DbExecutorException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DbExecutorImpl<T> implements DbExecutor<T> {

    private List<Field> fields = new ArrayList<>();

    public static <T> DbExecutorImpl<T> of(Class<T> clazz) {
        DbExecutorImpl<T> executor = new DbExecutorImpl<>();
        executor.getClassFields(clazz);
        return executor;
    }

    private DbExecutorImpl() {
    }

    @Override
    public void create(T objectData) throws DbExecutorException {
        PrimaryField primaryKey = this.getPrimaryKey(objectData);
        String sql = SqlManager.generateInsert(this.fields, primaryKey.field);

        try (Connection connection = SqlManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            List<Field> fieldsWithoutId = this.getFieldsWithoutId();
            for (int i = 0; i < fieldsWithoutId.size(); i++)
                statement.setString(i + 1, fieldsWithoutId.get(i).get(objectData).toString());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Field declaredField = objectData.getClass().getDeclaredField(primaryKey.field.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(objectData, generatedKeys.getObject(1));
                }
            }
        } catch (Exception e) {
            throw new DbExecutorException(e.getMessage());
        }
    }

    @Override
    public void update(T objectData) throws DbExecutorException {
        PrimaryField primaryKey = this.getPrimaryKey(objectData);
        String sql = SqlManager.generateUpdate(this.fields, primaryKey.field);

        try (Connection connection = SqlManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            List<Field> fieldsWithoutId = this.getFieldsWithoutId();
            for (int i = 0; i < fieldsWithoutId.size(); i++)
                statement.setString(i + 1, fieldsWithoutId.get(i).get(objectData).toString());

            statement.setString(fieldsWithoutId.size() + 1, primaryKey.value.toString());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DbExecutorException(e.getMessage());
        }
    }

    @Override
    public void createOrUpdate(T objectData) throws DbExecutorException {
        if (this.getPrimaryKey(objectData).value != null)
            this.update(objectData);
        else
            this.create(objectData);
    }

    @Override
    public T load(long id, Class<T> clazz) throws DbExecutorException {
        Field primaryKey = fields.stream().filter(field -> Arrays.stream(field.getDeclaredAnnotations())
                                                                 .anyMatch(annotation -> annotation.annotationType().equals(Id.class)))
                                                                 .findFirst()
                                                                 .orElseThrow(() -> new DbExecutorException("В классе " + clazz.getSimpleName() + " должно быть одно поле помеченное ключом @Id"));

        String sql = SqlManager.generateSelect(this.fields, primaryKey);

        try (Connection connection = SqlManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, String.valueOf(id));

            try (ResultSet resultSet = statement.executeQuery()) {
                T object = clazz.getDeclaredConstructor().newInstance();
                if (resultSet.first()) {
                    for (Field field : this.fields) {
                        Field declaredField = object.getClass().getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        declaredField.set(object, resultSet. getObject(field.getName().toUpperCase()));
                    }
                }
                return object;
            }
        } catch (Exception e) {
            throw new DbExecutorException(e.getMessage());
        }
    }

    private void getClassFields(Class<T> clazz) {
        this.fields = Arrays.stream(clazz.getDeclaredFields()).peek(field -> field.setAccessible(true)).collect(toList());
    }

    private List<Field> getFieldsWithoutId() {
        return this.fields.stream().filter(field -> Arrays.stream(field.getDeclaredAnnotations())
                .noneMatch(annotation -> annotation.annotationType().equals(Id.class))).collect(toList());
    }

    private PrimaryField getPrimaryKey(T objectData) throws DbExecutorException {
        Supplier<Stream<Field>> primaryKeysStream = () -> this.fields.stream().filter(field -> Arrays.stream(field.getDeclaredAnnotations())
                                                                                              .anyMatch(annotation -> annotation.annotationType().equals(Id.class)));
        if (primaryKeysStream.get().count() == 1) {
            try {
                Field primaryField = primaryKeysStream.get().findFirst().get();
                return new PrimaryField(primaryField, primaryField.get(objectData));
            } catch (Exception e) {
                throw new DbExecutorException(e.getMessage());
            }
        } else
            throw new DbExecutorException("В классе " + objectData.getClass().getSimpleName() + " должно быть одно поле помеченное ключом @Id");
    }

    private class PrimaryField {

        private Field field;
        private Object value;

        private PrimaryField(Field field, Object value) {
            this.field = field;
            this.value = value;
        }
    }
}
