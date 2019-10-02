package ru.otus.l14.jdbc;

public interface DbExecutor<T> {

    void create(T objectData);

    void update(T objectData);

    void createOrUpdate(T objectData);

    T load(long id, Class<T> clazz);
}
