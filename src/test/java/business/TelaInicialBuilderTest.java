package business;

import models.Cargo;
import models.Usuario;
import org.junit.jupiter.api.*;
import presenter.CadastroPresenter;
import presenter.LoginPresenter;
import repository.CargoRepository;
import repository.EntityManagerProvider;
import repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TelaInicialBuilderTest {

    UsuarioRepository repository;
    CargoRepository cargoRepository;

    @BeforeAll
    void setUp() {
        this.repository = UsuarioRepository.getInstance(true);
        this.cargoRepository = CargoRepository.getInstance(true);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.repository.clearEntityManager();
        this.cargoRepository.deleteAll();
    }

    /**
     * Função getTelaInicial()
     Deve retornar a tela de <b>cadastro</b> quando não existe um usuário cadastrado no banco.
     */
    @Test
    void CT031() {
        assertTrue(TelaInicialBuilder.getTelaInicial() instanceof CadastroPresenter);
    }

    @Test
    void CT032() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);
        this.repository.save(usuario);
        assertTrue(TelaInicialBuilder.getTelaInicial() instanceof LoginPresenter);
    }
}