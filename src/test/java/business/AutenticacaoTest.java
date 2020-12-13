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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AutenticacaoTest {

    Autenticacao autenticacao;
    UsuarioRepository usuarioRepository;

    @BeforeAll
    void setUp() {
        this.autenticacao = new Autenticacao();
        this.usuarioRepository = UsuarioRepository.getInstance();
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
}
