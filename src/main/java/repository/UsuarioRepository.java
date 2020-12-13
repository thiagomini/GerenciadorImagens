package repository;

import models.Cargo;
import models.Usuario;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {

    private static UsuarioRepository uniqueInstance;

    private EntityManager entityManager;

    private UsuarioRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static synchronized UsuarioRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UsuarioRepository(
                    EntityManagerProvider.getEntityManager()
            );
        }
        return uniqueInstance;
    }

    public Optional<Usuario> findById(long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        return usuario != null ? Optional.of(usuario) : Optional.empty();
    }

    public List<Usuario> findAll() {
        return entityManager.createQuery("from Usuario").getResultList();
    }

    public Optional<Usuario> findByName(String name) {
        Usuario usuario = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.name = :name", Usuario.class)
                .setParameter("name", name)
                .getSingleResult();
        return usuario != null ? Optional.of(usuario) : Optional.empty();
    }

    public Optional<Usuario> save(Usuario usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return Optional.of(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(Usuario usuario) {
        try {
            entityManager.getTransaction().begin();
            usuario = entityManager.find(Usuario.class, usuario.getId());
            entityManager.remove(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void deleteById(long id) {
        Optional<Usuario> usuario = findById(id);
        usuario.ifPresent(this::delete);
    }

    public void deleteAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Usuario").executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * Indica se existe algum usuário cadastrado no banco
     * @return Verdadeiro se existe um usuário registrado no banco, falso caso contrário
     */
    public boolean hasOneRegistered() {
        return count() > 0;
    }

    public long count() {
        return (long) entityManager.createQuery("SELECT count (u.id) FROM Usuario u")
                .getSingleResult();

    }

    public void clearEntityManager() {
        this.entityManager.clear();
    }
}
