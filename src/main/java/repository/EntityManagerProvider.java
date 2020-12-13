package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagens");
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private EntityManagerProvider(){}
}
