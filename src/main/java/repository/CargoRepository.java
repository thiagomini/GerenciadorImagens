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
        Cargo cargo = entityManager.find(Cargo.class, id);
        return cargo != null ? Optional.of(cargo) : Optional.empty();
    }
    public List<Cargo> findAll() {
        return entityManager.createQuery("from Cargo").getResultList();
    }
    public Optional<Cargo> findByName(String name) {
        Cargo cargo = entityManager.createNamedQuery("Cargo.findByName", Cargo.class)
                .setParameter("name", name)
                .getSingleResult();
        return cargo != null ? Optional.of(cargo) : Optional.empty();
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
