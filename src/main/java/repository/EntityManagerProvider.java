package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagens");
    private static final EntityManagerFactory testEntityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagensInMemory");
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static EntityManager getTestEntityManager() {
        return testEntityManagerFactory.createEntityManager();
    }

    private EntityManagerProvider(){}
}
