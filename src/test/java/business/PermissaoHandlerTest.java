package business;

import models.Cargo;
import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repository.ImagemRepository;
import repository.PermissoesImagemRepository;
import repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PermissaoHandlerTest {

    PermissaoHandler permissaoHandler;
    PermissoesImagemRepository permissoesImagemRepository;
    UsuarioRepository usuarioRepository;
    ImagemRepository imagemRepository;

    @BeforeAll
    void beforeAll() {
        this.permissaoHandler = new PermissaoHandler(true);
        this.permissoesImagemRepository = PermissoesImagemRepository.getInstance(true);
        this.usuarioRepository = UsuarioRepository.getInstance(true);
        this.imagemRepository = ImagemRepository.getInstance(true);
        usuarioRepository.deleteAll();
    }

    /**
     * Função <b>definirPermissao</b>
     * Deve editar as permissões corretamente
     */
    @Test
    void CT046() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                false,
                false,
                false
        );
        this.permissoesImagemRepository.save(permissaoImagem);

        this.permissaoHandler.definirPermissoes(usuario, imagem, true, false, true);

        assertTrue(permissaoImagem.isVisualizacao());
        assertFalse(permissaoImagem.isExclusao());
        assertTrue(permissaoImagem.isCompartilhamento());
    }

    /**
     * Função <b>definirPermissao</b>
     * Deve criar as permissões corretamente quando não há uma permissão já cadastrada
     */
    @Test
    void CT047() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Beltrano", "beltrano@mail.com", "1234", cargo);

        this.permissaoHandler.definirPermissoes(usuario, imagem, true, false, true);

        Optional<PermissaoImagem> permissaoImagemOptional = this.permissoesImagemRepository.findByUserAndImage(usuario.getId(), imagem.getId());
        PermissaoImagem permissaoImagem = permissaoImagemOptional.get();

        assertTrue(permissaoImagem.isVisualizacao());
        assertFalse(permissaoImagem.isExclusao());
        assertTrue(permissaoImagem.isCompartilhamento());
    }

    /**
     * Função <b>temPermissaoParaVisualizar</b>
     * Deve retornar falso quando o usuário não tem permissão para visualizar
     */
    @Test
    void CT048() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);

        assertFalse(this.permissaoHandler.temPermissaoParaVisualizar(usuario, imagem));
    }

    /**
     * Função <b>temPermissaoParaVisualizar</b>
     * Deve retornar verdadeiro quando o usuário tem permissão para visualizar
     */
    @Test
    void CT049() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                true,
                false,
                false
        );
        this.permissoesImagemRepository.save(permissaoImagem);
        assertTrue(this.permissaoHandler.temPermissaoParaVisualizar(usuario, imagem));
    }

    /**
     * Função <b>temPermissaoParaVisualizar</b>
     * Deve retornar <b>verdadeiro</b>> quando o usuário é um admin
     */
    @Test
    void CT050() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Administrador", "admin");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);
        assertTrue(this.permissaoHandler.temPermissaoParaVisualizar(usuario, imagem));
    }


    /**
     * Função <b>temPermissaoParaCompartilhar(usuario, imagem)</b>
     * Deve retornar <b>falso</b>> quando o usuário não possui permissão
     */
    @Test
    void CT051() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);

        assertFalse(this.permissaoHandler.temPermissaoParaCompartilhar(usuario, imagem));
    }

    /**
     * Função <b>temPermissaoParaCompartilhar(usuario, imagem)</b>
     * Deve retornar <b>verdadeiro</b>> quando o usuário possui permissão
     */
    @Test
    void CT052() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                false,
                false,
                true
        );
        this.permissoesImagemRepository.save(permissaoImagem);
        assertTrue(this.permissaoHandler.temPermissaoParaCompartilhar(usuario, imagem));
    }

    /**
     * Função <b>temPermissaoParaCompartilhar(usuario, imagem)</b>
     * Deve retornar <b>verdadeiro</b>> quando o usuário é admin
     */
    @Test
    void CT053() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Admin", "admin");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);

        assertTrue(this.permissaoHandler.temPermissaoParaCompartilhar(usuario, imagem));
    }

    /**
     * Função <b>temPermissaoParaExcluir(usuario, imagem)</b>
     * Deve retornar <b>falso</b>> quando o usuário não possui permissão
     */
    @Test
    void CT054() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);

        assertFalse(this.permissaoHandler.temPermissaoParaExcluir(usuario, imagem));
    }

    /**
     * Função <b>temPermissaoParaExcluir(usuario, imagem)</b>
     * Deve retornar <b>verdadeiro</b>> quando o usuário possui permissão
     */
    @Test
    void CT055() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                false,
                true,
                false
        );
        this.permissoesImagemRepository.save(permissaoImagem);
        assertTrue(this.permissaoHandler.temPermissaoParaExcluir(usuario, imagem));
    }

    /**
     * Função <b>temPermissaoParaExcluir(usuario, imagem)</b>
     * Deve retornar <b>verdadeiro</b>> quando o usuário é admin
     */
    @Test
    void CT056() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Admin", "admin");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);

        assertTrue(this.permissaoHandler.temPermissaoParaExcluir(usuario, imagem));
    }


}
