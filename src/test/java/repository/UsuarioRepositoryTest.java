package repository;

import models.Cargo;
import models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {

    UsuarioRepository repository;

    @BeforeAll
    void setUp() {
        this.repository = UsuarioRepository.getInstance(true);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.repository.clearEntityManager();
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

    /**
     * Função <b>count()</b>
     * Deve retornar corretamente o número de usuários cadastrados
     */
    @Test
    void CT027() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);
        Usuario usuario2 = new Usuario("Thiago2", "thiago@mail.com" , "1234", cargo);
        Usuario usuario3 = new Usuario("Thiago3", "thiago@mail.com" , "1234", cargo);
        this.repository.save(usuario);
        this.repository.save(usuario2);
        this.repository.save(usuario3);
        assertEquals(3, this.repository.count());
    }

    /**
     * Função <b>hasOneRegistered()</b>
     * Deve retornar se existe algum usuário cadastrado no banco de dados
     */
    @Test
    void CT028() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);
        this.repository.save(usuario);
        assertTrue(this.repository.hasOneRegistered());
    }






}