package repository;

import models.Cargo;
import models.Imagem;
import models.Notificacao;
import models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificacaoRepositoryTest {

    EntityManager entityManager;
    NotificacaoRepository repository;

    @BeforeAll
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagensInMemory");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.repository = new NotificacaoRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.entityManager.clear();
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
        Usuario usuario2 = new Usuario("Fulano", "beltrano@email.com", "1234", cargo);
        Notificacao notificacao2 = new Notificacao("A imagem foi compartilhada com você", imagem, usuario);

        this.repository.save(notificacao);
        this.repository.save(notificacao);
    }

}
