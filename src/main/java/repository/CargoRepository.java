package repository;

import models.Cargo;
import models.Imagem;
import models.PermissaoImagem;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class CargoRepository {

    private static CargoRepository uniqueInstance;
    
    private EntityManager entityManager;

    private CargoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static synchronized CargoRepository getInstance(boolean testDatabase) {
        if (uniqueInstance == null) {
            uniqueInstance = new CargoRepository(
                    testDatabase
                            ? EntityManagerProvider.getTestEntityManager()
                            : EntityManagerProvider.getEntityManager()

            );
        }
        return uniqueInstance;
    }


    public Optional<Cargo> findById(long id) {
        Cargo cargo = entityManager.find(Cargo.class, id);
        return cargo != null ? Optional.of(cargo) : Optional.empty();
    }
    public List<Cargo> findAll() {
        return entityManager.createQuery("from Cargo").getResultList();
    }
    public Optional<Cargo> findByCode(String code) {
        try {
            Cargo cargo = entityManager.createNamedQuery("Cargo.findByCode", Cargo.class)
                    .setParameter("code", code)
                    .getSingleResult();
            return Optional.of(cargo);
        } catch (NoResultException exception) {
            return Optional.empty();
        }

    }
    public Optional<Cargo> save(Cargo cargo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();
            return Optional.of(cargo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(Cargo cargo) {
        try {
            entityManager.getTransaction().begin();
            cargo = entityManager.find(Cargo.class, cargo.getId());
            entityManager.remove(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void deleteById(long id) {
        Optional<Cargo> cargo = findById(id);
        cargo.ifPresent(this::delete);
    }

    public void deleteAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Cargo").executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void clearEntityManager() {
        this.entityManager.clear();
    }
}
