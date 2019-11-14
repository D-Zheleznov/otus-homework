package ru.otus.l17.service;

import java.util.List;
import java.util.UUID;

public interface DBService<T> {

    void create(T object);

    void update(T object);

    T load(UUID id, Class<T> clazz);

    List<T> loadAll(Class<T> clazz);

    void dropCollection();
}
