package factory;

import models.Cargo;
import models.Usuario;
import org.junit.jupiter.api.*;
import repository.CargoRepository;
import repository.EntityManagerProvider;
import repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioFactoryTest {

    UsuarioRepository repository;
    CargoRepository cargoRepository;
    UsuarioFactory usuarioFactory;
    @BeforeAll
    void setUp() {
        this.repository = UsuarioRepository.getInstance(true);
        this.cargoRepository = CargoRepository.getInstance(true);
        this.usuarioFactory = new UsuarioFactory(true);
    }

    @AfterEach
    void tearDown() {
        this.cargoRepository.deleteAll();
        this.cargoRepository.clearEntityManager();
    }

    /**
     * Função <b>createUsuario(nome, email, senha)</b>
     * Deve criar corretamente um cargo de administrador e um usuário com o cargo admin
     */
    @Test
    void CT037() {
        Usuario usuarioNovo = usuarioFactory.createUsuario("Thiago", "email@mail.com", "1234");
        assertEquals("admin", usuarioNovo.getCargo().getCode());
        assertTrue(usuarioNovo.getCargo().getId() > 0);
    }

    /**
     * Função <b>createUsuario(nome, email, senha)</b>
     * Deve criar corretamente um usuário com o cargo admin
     */
    @Test
    void CT038() {
        Cargo cargo = new Cargo("Administrador Criado Antes", "admin");
        this.cargoRepository.save(cargo);

        Usuario usuarioNovo = usuarioFactory.createUsuario("Thiago", "email@mail.com", "1234");
        assertEquals("admin", usuarioNovo.getCargo().getCode());
        assertEquals("Administrador Criado Antes", usuarioNovo.getCargo().getNome());
    }

    /**
     * Função <b>createUsuario(nome, email, senha)</b>
     * Deve criar corretamente um cargo de common_user e um usuário com esse cargo
     */
    @Test
    void CT041() {
        Usuario usuarioAntigo = new Usuario("Antigo Usuario", "email", "senha");
        this.repository.save(usuarioAntigo);

        Usuario usuarioNovo = usuarioFactory.createUsuario("Thiago", "email@mail.com", "1234");
        assertEquals("common_user", usuarioNovo.getCargo().getCode());
        assertTrue(usuarioNovo.getCargo().getId() > 0);
    }

    /**
     * Função <b>createUsuario(nome, email, senha)</b>
     * Deve criar corretamente um usuário com o cargo common_user
     */
    @Test
    void CT043() {
        Usuario usuarioAntigo = new Usuario("Antigo Usuario", "email", "senha");
        this.repository.save(usuarioAntigo);

        this.cargoRepository.save(new Cargo("Usuario Comum", "common_user"));

        Usuario usuarioNovo = usuarioFactory.createUsuario("Thiago", "email@mail.com", "1234");
        assertEquals("common_user", usuarioNovo.getCargo().getCode());
        assertTrue(usuarioNovo.getCargo().getId() > 0);
    }



}