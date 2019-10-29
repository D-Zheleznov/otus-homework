package ru.otus.l15.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;

public class HibernateManagerImpl<T> implements HibernateManager<T> {

    private SessionFactory sessionFactory;

    public static <T> HibernateManagerImpl<T> of(String configFileName, Class... classes) {
        HibernateManagerImpl<T> hibernateManager = new HibernateManagerImpl<>();
        Configuration configuration = new Configuration().configure(configFileName);
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        MetadataSources metadataSources = new MetadataSources(registry);
        Arrays.stream(classes).forEach(metadataSources::addAnnotatedClass);
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        hibernateManager.sessionFactory = metadata.getSessionFactoryBuilder().build();
        return hibernateManager;
    }

    private HibernateManagerImpl() {
    }

    @Override
    public void create(T object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(T object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public T load(long id, Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(clazz, id);
        }
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void closeSessionFactory() {
        sessionFactory.close();
    }
}
