package business;

import models.Cargo;
import models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import presenter.CadastroPresenter;
import presenter.LoginPresenter;
import repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TelaInicialBuilderTest {

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