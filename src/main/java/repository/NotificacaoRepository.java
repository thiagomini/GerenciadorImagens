package repository;

import models.Notificacao;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class NotificacaoRepository {
    private EntityManager entityManager;

    public NotificacaoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Notificacao> findById(long id) {
        Notificacao notificacao = entityManager.find(Notificacao.class, id);
        return notificacao != null ? Optional.of(notificacao) : Optional.empty();
    }

    public List<Notificacao> findAll() {
        return entityManager.createQuery("from Notificacao").getResultList();
    }

    public Optional<Notificacao> save(Notificacao notificacao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(notificacao);
            entityManager.getTransaction().commit();
            return Optional.of(notificacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(Notificacao notificacao) {
        try {
            entityManager.getTransaction().begin();
            notificacao = entityManager.find(Notificacao.class, notificacao.getId());
            entityManager.remove(notificacao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void deleteById(long id) {
        Optional<Notificacao> notificacao = findById(id);
        notificacao.ifPresent(this::delete);
    }

    public void deleteAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Notificacao").executeUpdate();
        entityManager.getTransaction().commit();
    }
}
