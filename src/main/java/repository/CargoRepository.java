package repository;

import models.Cargo;
import models.Imagem;
import models.PermissaoImagem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CargoRepository {
    
    private EntityManager entityManager;

    public CargoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public Optional<Cargo> findById(long id) {
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
}
