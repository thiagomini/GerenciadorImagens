package repository;

import models.Cargo;
import models.Imagem;
import models.Notificacao;
import models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificacaoRepositoryTest {

    NotificacaoRepository repository;
    CargoRepository cargoRepository;

    @BeforeAll
    void setUp() {
        this.repository = NotificacaoRepository.getInstance(true);
        this.cargoRepository = CargoRepository.getInstance(true);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.repository.clearEntityManager();
        this.cargoRepository.deleteAll();
    }



    /**
     * Função <b>save()</b>
     * Deve salvar corretamente uma notificação no banco de dados
     */
    @Test
    void CT022() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@email.com", "1234", cargo);
        Notificacao notificacao = new Notificacao("A imagem foi compartilhada com você", imagem, usuario);
        Optional<Notificacao> notificacaoOptional = this.repository.save(notificacao);
        assertTrue(notificacaoOptional.isPresent());
    }

    /**
     * Função <b>findById()</b>
     * Deve encontrar corretamente uma notificação no banco de dados pelo id
     */
    @Test
    void CT023() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@email.com", "1234", cargo);
        Notificacao notificacao = new Notificacao("A imagem foi compartilhada com você", imagem, usuario);
        this.repository.save(notificacao);

        Optional<Notificacao> notificacaoOptional = this.repository.findById(notificacao.getId());
        assertTrue(notificacaoOptional.isPresent());
        assertEquals(notificacao.getId(), notificacaoOptional.get().getId());
    }

    /**
     * Função <b>findAll()</b>
     * Deve encontrar corretamente todas as notificações do banco
     */
    @Test
    void CT024() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Administrador", "admin");
        Usuario usuario = new Usuario("Fulano", "fulano@email.com", "1234", cargo);
        Notificacao notificacao = new Notificacao("A imagem foi compartilhada com você", imagem, usuario);

        Imagem imagem2 = new Imagem("imagem2.jpg");
        Cargo cargo2 = new Cargo("Usuario", "user");
        Usuario usuario2 = new Usuario("Fulano", "beltrano@email.com", "1234", cargo2);
        Notificacao notificacao2 = new Notificacao("A imagem foi compartilhada com você", imagem2, usuario2);

        this.repository.save(notificacao);
        this.repository.save(notificacao2);

        ArrayList<Notificacao> notificacoes = (ArrayList<Notificacao>) this.repository.findAll();
        assertEquals(2, notificacoes.size());
    }

    /**
     * Função <b>delete(notificacao)</b>
     * Deve deletar corretamente uma notificacao pela referência
     */
    @Test
    void CT025() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@email.com", "1234", cargo);
        Notificacao notificacao = new Notificacao("A imagem foi compartilhada com você", imagem, usuario);

        this.repository.save(notificacao);

        this.repository.delete(notificacao);
        assertTrue(this.repository.findById(notificacao.getId()).isEmpty());
    }

    /**
     * Função <b>deleteById(id)</b>
     * Deve deletar corretamente uma imagem pelo Id
     */
    @Test
    void CT026() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@email.com", "1234", cargo);
        Notificacao notificacao = new Notificacao("A imagem foi compartilhada com você", imagem, usuario);

        this.repository.save(notificacao);

        this.repository.deleteById(notificacao.getId());
        assertTrue(this.repository.findById(notificacao.getId()).isEmpty());
    }

    @Test
    void CT064() {
        Imagem imagem = new Imagem("imagem.jpg");
        Cargo cargo = new Cargo("Usuario", "user");
        Usuario usuario = new Usuario("Fulano", "fulano@email.com", "1234", cargo);
        Notificacao notificacao = new Notificacao("A imagem foi compartilhada com você", imagem, usuario);

        this.repository.save(notificacao);

        List<Notificacao> notificacaoList = repository.findByUser(
                usuario.getId()
        );

        assertEquals(1, notificacaoList.size());
    }

}
