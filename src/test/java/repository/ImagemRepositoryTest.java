package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import models.Imagem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImagemRepositoryTest {

    EntityManager entityManager;
    ImagemRepository repository;

    @BeforeAll
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagens");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.repository = new ImagemRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.clear();
    }

    /**
     * Função <b>save()</b>
     * Deve salvar corretamente uma imagem no banco de dados
     */
    @Test
    void CT01() {
        Imagem imagem = new Imagem("imagem.jpg");
        Optional<Imagem> imagemOptional = this.repository.save(imagem);

        assertTrue(imagemOptional.isPresent());

        Optional<Imagem> imagemRecuperada = this.repository.findById(imagemOptional.get().getId());
        assertTrue(imagemRecuperada.isPresent());
        assertEquals(imagemOptional.get().getId(), imagemRecuperada.get().getId());
    }

    /**
     * Função <b>save()</b>
     * Deve salvar corretamente uma imagem no banco de dados
     */
    @Test
    void CT02() {
        Imagem imagem = new Imagem("imagem.jpg");
        Optional<Imagem> imagemOptional = this.repository.save(imagem);

        assertTrue(imagemOptional.isPresent());

        Optional<Imagem> imagemRecuperada = this.repository.findById(imagemOptional.get().getId());
        assertTrue(imagemRecuperada.isPresent());
        assertEquals(imagemOptional.get().getId(), imagemRecuperada.get().getId());
    }

}