package repository;

import models.Imagem;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class ImagemRepository {
    private EntityManager entityManager;

    private static ImagemRepository uniqueInstance;

    private ImagemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static synchronized ImagemRepository getInstance(boolean testDatabase) {
        if (uniqueInstance == null) {
            uniqueInstance = new ImagemRepository(
                    testDatabase
                            ? EntityManagerProvider.getTestEntityManager()
                            : EntityManagerProvider.getEntityManager()
            );
        }

        return uniqueInstance;
    }

    /**
     * Encontra uma imagem no banco de dados pelo Id
     * @param id Id da imagem
     * @return Imagem encontrada
     */
    public Optional<Imagem> findById(long id) {
        Imagem imagem = entityManager.find(Imagem.class, id);
        return imagem != null ? Optional.of(imagem) : Optional.empty();
    }
    public List<Imagem> findAll() {
        return entityManager.createQuery("from Imagem").getResultList();
    }

    public Optional<Imagem> findByCaminho(String caminho) {
        try {
            Imagem imagem = entityManager.createNamedQuery("Imagem.findByCaminho", Imagem.class)
                    .setParameter("caminho", caminho)
                    .getSingleResult();
            return Optional.of(imagem);
        } catch (NoResultException e) {
            return Optional.empty();
        }


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

    public Optional<Imagem> merge(Imagem imagem) {
        Imagem imagemAtualizada;
        try {
            entityManager.getTransaction().begin();
            imagemAtualizada = entityManager.merge(imagem);
            entityManager.getTransaction().commit();
            return Optional.of(imagemAtualizada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(Imagem imagem) {
       imagem.setExcluida(true);
       this.save(imagem);
    }

    public void deleteById(long id) {
            Optional<Imagem> imagem = findById(id);
            imagem.ifPresent(this::delete);
    }

    public void deleteAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Imagem").executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void clearEntityManager() {
        this.entityManager.clear();
    }
}
