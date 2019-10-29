package ru.otus.l15.hibernate;

import org.hibernate.SessionFactory;

public interface HibernateManager<T> {

    void create(T object);

    void update(T object);

    T load(long id, Class<T> clazz);

    SessionFactory getSessionFactory();

    void closeSessionFactory();
}
