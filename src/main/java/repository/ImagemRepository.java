package repository;

import models.Imagem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ImagemRepository {
    private EntityManager entityManager;

    public ImagemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Imagem> findById(long id) {
        Imagem imagem = entityManager.find(Imagem.class, id);
        return imagem != null ? Optional.of(imagem) : Optional.empty();
    }
    public List<Imagem> findAll() {
        return entityManager.createQuery("from Imagem").getResultList();
    }
    public Optional<Imagem> findByName(String name) {
        Imagem imagem = entityManager.createNamedQuery("Imagem.findByName", Imagem.class)
                .setParameter("name", name)
                .getSingleResult();
        return imagem != null ? Optional.of(imagem) : Optional.empty();
    }
    public Optional<Imagem> save(Imagem imagem) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(imagem);
            entityManager.getTransaction().commit();
            return Optional.of(imagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(Imagem imagem) {
        try {
            entityManager.getTransaction().begin();
            imagem = entityManager.find(Imagem.class, imagem.getId());
            entityManager.remove(imagem);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void deleteById(long id) {
            Optional<Imagem> imagem = findById(id);
            imagem.ifPresent(this::delete);
    }
}
