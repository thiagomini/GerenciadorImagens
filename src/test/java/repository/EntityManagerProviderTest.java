package repository;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerProviderTest {

    /**
     * Função <b>getEntityManager</b>
     * Deve retornar um Entity Manager que não seja null
     */
    @Test
    void CT029() {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        assertEquals("GerenciadorDeImagens", entityManager.getEntityManagerFactory().getProperties().get("hibernate.ejb.persistenceUnitName"));;
    }

    /**
     * Função <b>getEntityManager</b>
     * Deve retornar dois Entity Managers diferentes
     */
    @Test
    void CT030() {
        EntityManager entityManager1 = EntityManagerProvider.getEntityManager();
        EntityManager entityManager2 = EntityManagerProvider.getEntityManager();

        assertNotEquals(entityManager1, entityManager2);

    }

    /**
     * Função <b>getTestEntityManager</b>
     * Deve retornar um Entity Manager que não seja null
     */
    @Test
    void CT039() {
        EntityManager entityManager = EntityManagerProvider.getTestEntityManager();
        assertEquals("GerenciadorDeImagensInMemory", entityManager.getEntityManagerFactory().getProperties().get("hibernate.ejb.persistenceUnitName"));
    }
}