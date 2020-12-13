package business;

import models.Cargo;
import models.Usuario;
import org.junit.jupiter.api.*;
import presenter.JanelaPrincipalPresenter;
import presenter.states.JanelaPrincipalDeslogadoPrimeiroUsuarioState;
import presenter.states.JanelaPrincipalDeslogadoState;
import repository.CargoRepository;
import repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TelaInicialBuilderTest {

    UsuarioRepository repository;
    CargoRepository cargoRepository;
    JanelaPrincipalPresenter janelaPrincipalPresenter;

    @BeforeEach
    void initialSetup() {
        janelaPrincipalPresenter = new JanelaPrincipalPresenter(false);
    }

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
        janelaPrincipalPresenter.closeView();
    }

    /**
     * Função <b>getTelaInicial()</b>
     Deve retornar a tela de <b>cadastro</b> quando não existe um usuário cadastrado no banco.
     */
    @Test
    void CT031() {
        assertTrue(TelaInicialBuilder.getEstadoInicial(janelaPrincipalPresenter) instanceof JanelaPrincipalDeslogadoPrimeiroUsuarioState);
    }

    /**
     * Função <b>getTelaInicial()</b>
     Deve retornar a tela de <b>Login</b> quando existe um usuário cadastrado no banco.
     */
    @Test
    void CT032() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Thiago", "thiago@mail.com" , "1234", cargo);
        this.repository.save(usuario);
        assertTrue(TelaInicialBuilder.getEstadoInicial(janelaPrincipalPresenter) instanceof JanelaPrincipalDeslogadoState);
    }
}