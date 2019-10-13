package ru.otus.l14.jdbc;

import ru.otus.l14.jdbc.exceptions.DbExecutorException;

public interface DbExecutor<T> {

    void create(T objectData) throws DbExecutorException;

    void update(T objectData) throws DbExecutorException;

    void createOrUpdate(T objectData) throws DbExecutorException;

    T load(long id, Class<T> clazz) throws DbExecutorException;
}
