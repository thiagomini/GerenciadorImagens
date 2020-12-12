package repository;

import models.Cargo;
import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PermissoesImagemRepositoryTest {

    EntityManager entityManager;
    PermissoesImagemRepository repository;

    @BeforeAll
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagensInMemory");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.repository = new PermissoesImagemRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.entityManager.clear();
    }

    /**
     * Função <b>save()</b>
     * Deve salvar corretamente uma imagem no banco de dados
     */
    @Test
    void CT01() {
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


}