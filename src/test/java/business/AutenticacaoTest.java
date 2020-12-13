package business;

import models.Cargo;
import models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AutenticacaoTest {

    Autenticacao autenticacao;
    UsuarioRepository usuarioRepository;

    @BeforeAll
    void setUp() {
        this.autenticacao = new Autenticacao(true);
        this.usuarioRepository = UsuarioRepository.getInstance(true);
    }

    @AfterEach
    void tearDown() {
        this.usuarioRepository.deleteAll();
        this.usuarioRepository.clearEntityManager();
    }

    /**
     * Função <b>autenticar(email, password)</b>
     * Deve autentincar um usuário já cadastrado corretamente
     */
    @Test
    void CT033() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com" , "1234", cargo);
        Optional<Usuario> usuarioOptional = this.usuarioRepository.save(usuario);

        Usuario usuarioAutenticado = this.autenticacao.autenticar(usuario.getEmail(), usuario.getPassword());
        assertEquals(usuario, usuarioAutenticado);
    }


    /**
     * Função <b>autenticar(email, password)</b>
     * Deve ser levantado um erro com a mensagem “E-mail ou senha estão incorretos.”
     * ao tentar autenticar um usuário não cadastrado
     */
    @Test
    void CT035() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> this.autenticacao.autenticar("Usuario Inexistente", "123456"));
        assertEquals("E-mail ou senha estão incorretos.", exception.getMessage());
    }

    /**
     * Função <b>autenticar(email, password)</b>
     * Deve ser levantado um erro com a mensagem “E-mail ou senha estão incorretos.”
     * ao tentar autenticar um usuário cuja senha informada está incorreta
     */
    @Test
    void CT036() {
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com" , "1234", cargo);
        Optional<Usuario> usuarioOptional = this.usuarioRepository.save(usuario);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> this.autenticacao.autenticar(usuario.getEmail(), "abc"));
        assertEquals("E-mail ou senha estão incorretos.", exception.getMessage());
    }
}
