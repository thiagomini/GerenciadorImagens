package repository;

import models.Cargo;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CargoRepository {
    
    private EntityManager entityManager;

    public CargoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public Optional<Cargo> findById(Integer id) {
        Cargo author = entityManager.find(Cargo.class, id);
        return author != null ? Optional.of(author) : Optional.empty();
    }
    public List<Cargo> findAll() {
        return entityManager.createQuery("from Cargo").getResultList();
    }
    public Optional<Cargo> findByName(String name) {
        Cargo author = entityManager.createNamedQuery("Cargo.findByName", Cargo.class)
                .setParameter("name", name)
                .getSingleResult();
        return author != null ? Optional.of(author) : Optional.empty();
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
}
