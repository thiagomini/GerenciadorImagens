package repository;

import models.Cargo;
import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PermissoesImagemRepositoryTest {

    PermissoesImagemRepository repository;

    @BeforeAll
    void setUp() {
        this.repository = PermissoesImagemRepository.getInstance(true);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.repository.clearEntityManager();
    }

    /**     * Função <b>save()</b>
     * Deve salvar corretamente uma imagem no banco de dados
     */
    @Test
    void CT007() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Administrador", "admin");
        Usuario usuario = new Usuario("Thiago", "thiago@email.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                true,
                true,
                true
        );


        Optional<PermissaoImagem> permissaoImagemOptional = this.repository.save(permissaoImagem);
        assertTrue(permissaoImagemOptional.isPresent());

    }

    /**
     * Função <b>findById()</b>
     * Deve encontrar corretamente uma imagem no banco de dados pelo id
     */
    @Test
    void CT008() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Administrador", "admin");
        Usuario usuario = new Usuario("Thiago", "thiago@email.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                true,
                true,
                true
        );


        this.repository.save(permissaoImagem);

        Optional<PermissaoImagem> permissaoImagemOptional = this.repository.findById(permissaoImagem.getId());
        assertTrue(permissaoImagemOptional.isPresent());
        assertEquals(permissaoImagem.getId(), permissaoImagemOptional.get().getId());

    }

    /**
     * Função <b>findAll()</b>
     * Deve encontrar corretamente Todas as imagens do banco
     */
    @Test
    void CT009() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Administrador", "admin");
        Usuario usuario = new Usuario("Thiago", "thiago@email.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                true,
                true,
                true
        );

        Imagem imagem2 = new Imagem("imagem2.jpg");
        Cargo cargo2 = new Cargo("Usuario", "user");
        Usuario usuario2 = new Usuario("Miqueas", "miqueas@email.com", "1234", cargo);
        PermissaoImagem permissaoImagem2 = new PermissaoImagem(
                usuario2,
                imagem2,
                true,
                true,
                true
        );


        this.repository.save(permissaoImagem);
        this.repository.save(permissaoImagem2);

        ArrayList<PermissaoImagem> listaPermissoes = (ArrayList<PermissaoImagem>) this.repository.findAll();
        assertEquals(2, listaPermissoes.size());

    }

    /**     * Função <b>delete(permissaoImagem)</b>
     * Deve deletar corretamente uma imagem pela referência
     */
    @Test
    void CT010() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Administrador", "admin");
        Usuario usuario = new Usuario("Thiago", "thiago@email.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                true,
                true,
                true
        );

        this.repository.save(permissaoImagem);
        this.repository.delete(permissaoImagem);
        assertTrue(this.repository.findById(permissaoImagem.getId()).isEmpty());
    }

    /**     * Função <b>deleteById(id)</b>
     * Deve deletar corretamente uma imagem pelo Id
     */
    @Test
    void CT011() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Administrador", "admin");
        Usuario usuario = new Usuario("Thiago", "thiago@email.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                true,
                true,
                true
        );

        this.repository.save(permissaoImagem);
        this.repository.deleteById(permissaoImagem.getId());
        assertTrue(this.repository.findById(permissaoImagem.getId()).isEmpty());
    }

    /**
     * Função <b>findByUserAndImage(usuario, imagem)</b>
     * Deve retornar as permissões do usuário para a imagem corretamente
     */
    @Test
    void CT044() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@mail.com", "1234", cargo);
        PermissaoImagem permissaoImagem = new PermissaoImagem(
                usuario,
                imagem,
                true,
                false,
                true
        );
        this.repository.save(permissaoImagem);
        Optional<PermissaoImagem> permissaoImagemOptional = this.repository.findByUserAndImage(usuario, imagem);
        assertEquals(permissaoImagem, permissaoImagemOptional.get());
    }

    /**
     * Função <b>update(permissao)</b>
     * Deve atualizar as permissões corretamente
     */
    @Test
    void CT045() {
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
        this.repository.save(permissaoImagem);

        permissaoImagem.setVisualizacao(true);
        permissaoImagem.setCompartilhamento(true);
        permissaoImagem.setExclusao(false);

        Optional<PermissaoImagem> permissaoImagemOptional = this.repository.update(permissaoImagem);
        assertEquals(permissaoImagem, permissaoImagemOptional.get());
    }

}