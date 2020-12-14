package repository;

import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class PermissoesImagemRepository {

    private static PermissoesImagemRepository uniqueInstance;

    private EntityManager entityManager;

    private PermissoesImagemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static synchronized PermissoesImagemRepository getInstance(boolean testDatabase) {
        if (uniqueInstance == null) {
            uniqueInstance = new PermissoesImagemRepository(
                    testDatabase
                            ? EntityManagerProvider.getTestEntityManager()
                            : EntityManagerProvider.getEntityManager()
            );
        }
        return uniqueInstance;
    }

    /**
     * Encontra uma permissão no banco de dados pelo Id
     * @param id Id da permissão
     * @return Imagem encontrada
     */
    public Optional<PermissaoImagem> findById(long id) {
        PermissaoImagem permissao = entityManager.find(PermissaoImagem.class, id);
        return permissao != null ? Optional.of(permissao) : Optional.empty();
    }

    public Optional<PermissaoImagem> findByUserAndImage(long usuarioId, long imagemId) {
        try {
            PermissaoImagem permissaoImagem = (PermissaoImagem) entityManager.createQuery(
                    "SELECT pi FROM PermissaoImagem pi WHERE pi.usuario.id = :usuarioId AND pi.imagem.id = :imagemId")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("imagemId", imagemId)
                    .getSingleResult();
            return Optional.of(permissaoImagem);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<PermissaoImagem> findAll() {
        return entityManager.createQuery("from PermissaoImagem").getResultList();
    }


    public Optional<PermissaoImagem> save(PermissaoImagem permissao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(permissao);
            entityManager.getTransaction().commit();
            return Optional.of(permissao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<PermissaoImagem> update(PermissaoImagem permissao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(permissao);
            entityManager.getTransaction().commit();
            return Optional.of(permissao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(PermissaoImagem permissao) {
        try {
            entityManager.getTransaction().begin();
            permissao = entityManager.find(PermissaoImagem.class, permissao.getId());
            entityManager.remove(permissao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void deleteById(long id) {
        Optional<PermissaoImagem> permissao = findById(id);
        permissao.ifPresent(this::delete);
    }

    public void deleteAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM PermissaoImagem").executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void clearEntityManager() {
        this.entityManager.clear();
    }
}
