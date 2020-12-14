package business;

import models.Cargo;
import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repository.PermissoesImagemRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PermissaoHandlerTest {

    PermissaoHandler permissaoHandler;
    PermissoesImagemRepository permissoesImagemRepository;

    @BeforeAll
    void beforeAll() {
        this.permissaoHandler = new PermissaoHandler(true);
        this.permissoesImagemRepository = PermissoesImagemRepository.getInstance(true);
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

        Optional<PermissaoImagem> permissaoImagemOptional = this.permissoesImagemRepository.findByUserAndImage(usuario, imagem);
        PermissaoImagem permissaoImagem = permissaoImagemOptional.get();

        assertTrue(permissaoImagem.isVisualizacao());
        assertFalse(permissaoImagem.isExclusao());
        assertTrue(permissaoImagem.isCompartilhamento());
    }

    /**
     * Função <b>temPermissaoParaVisualizar</b>
     * Deve criar as permissões corretamente quando não há uma permissão já cadastrada
     */
    @Test
    void CT048() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);

        assertEquals(false, this.permissaoHandler.temPermissaoParaVisualizar(usuario, imagem));
    }

    // TODO Definir CTs para quando o usuário é admin
    // TODO Definir CTs para quando já tem uma permissão cadastrada

}
