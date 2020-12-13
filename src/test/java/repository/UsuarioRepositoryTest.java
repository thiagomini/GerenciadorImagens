package repository;

import models.Cargo;
import models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {

    EntityManager entityManager;
    UsuarioRepository repository;

    @BeforeAll
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagensInMemory");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.repository = new UsuarioRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.entityManager.clear();
    }


    /**
     * Função <b>save(usuario)</b>
     * Deve salvar um novo usuario corretamente
     */
    @Test
    void CT012() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);

        Optional<Usuario> usuarioOptional = this.repository.save(usuario);

        assertTrue(usuarioOptional.isPresent());
        assertTrue(usuario.getId() > 0, "Id do usuario " + usuario.getId() + " deve existir.");
    }

    /**
     * Função <b>findById(id)</b>
     * Deve retornar corretamente um usuario pelo Id
     */
    @Test
    void CT013() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);
        this.repository.save(usuario);

        Optional<Usuario> usuarioEncontrado = this.repository.findById(usuario.getId());
        assertTrue(usuarioEncontrado.isPresent());
    }

    /**
     * Função <b>findAll()</b>
     * Deve retornar corretamente todos os usuarios
     */
    @Test
    void CT014() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);

        Usuario usuario2 = new Usuario("Miqueas", "miqueas@mail.com" , "1234", cargo);
        this.repository.save(usuario);
        this.repository.save(usuario2);

        ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) this.repository.findAll();

        assertEquals(2, listaUsuarios.size());
    }

    /**
     * Função <b>delete(usuario)</b>
     * Deve deletar corretamente um usuario pela referência
     */
    @Test
    void CT015() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);

        this.repository.save(usuario);

        this.repository.delete(usuario);

        Optional<Usuario> usuarioEncontrado = this.repository.findById(usuario.getId());

        assertTrue(usuarioEncontrado.isEmpty());
    }

    /**
     * Função <b>deleteById(id)</b>
     * Deve deletar corretamente um usuario pelo id
     */
    @Test
    void CT016() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);
        this.repository.save(usuario);

        this.repository.deleteById(usuario.getId());

        Optional<Usuario> usuarioEncontrado = this.repository.findById(usuario.getId());

        assertTrue(usuarioEncontrado.isEmpty());
    }





}