package repository;

import models.Imagem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImagemRepositoryTest {

    ImagemRepository repository;

    @BeforeAll
    void setUp() {
        this.repository = ImagemRepository.getInstance();
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.repository.clearEntityManager();
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
     * Função <b>findById()</b>
     * Deve encontrar uma imagem pelo ID
     */
    @Test
    void CT02() {
        Imagem imagem = new Imagem("imagem2.jpg");
        Optional<Imagem> imagemSalvaOptional = this.repository.save(imagem);

        if(imagemSalvaOptional.isEmpty()) {
            throw new Error("Erro no Caso de teste 02 - Imagem não foi salva");
        }

        Imagem imagemSalva = imagemSalvaOptional.get();

        Optional<Imagem> imagemRecuperada = this.repository.findById(imagemSalva.getId());
        assertTrue(imagemRecuperada.isPresent());
        assertEquals(imagemSalva.getId(), imagemRecuperada.get().getId());

    }

    /**
     * Função <b>delete()</b>
     * Deve setar o campo "deletada" para true de uma imagem
     */
    @Test
    void CT03() {
        Imagem imagem = new Imagem("imagem3.jpg");
        Optional<Imagem> imagemSalvaOptional = this.repository.save(imagem);

        if(imagemSalvaOptional.isEmpty()) {
            throw new Error("Erro no Caso de teste 03 - Imagem não foi salva");
        }

        Imagem imagemSalva = imagemSalvaOptional.get();

        this.repository.delete(imagemSalva);
        assertTrue(imagemSalva.isExcluida());

    }

    /**
     * Função <b>deleteById()</b>
     * Deve setar o campo "deletada" para true de uma imagem
     */
    @Test
    void CT04() {
        Imagem imagem = new Imagem("imagem4.jpg");
        Optional<Imagem> imagemSalvaOptional = this.repository.save(imagem);

        if(imagemSalvaOptional.isEmpty()) {
            throw new Error("Erro no Caso de teste 04 - Imagem não foi salva");
        }

        Imagem imagemSalva = imagemSalvaOptional.get();

        this.repository.deleteById(imagemSalva.getId());
        assertTrue(imagemSalva.isExcluida());

    }

    /**
     * Função <b>findAll()</b>
     * Deve retornar uma lista com todas as imagens do banco
     */
    @Test
    void CT05() {
        Imagem imagem = new Imagem("imagem5.jpg");
        Imagem imagem2 = new Imagem("imagem6.jpg");
        Optional<Imagem> imagemSalvaOptional = this.repository.save(imagem);
        Optional<Imagem> imagem2SalvaOptional = this.repository.save(imagem2);

        if(imagemSalvaOptional.isEmpty() || imagem2SalvaOptional.isEmpty()) {
            throw new Error("Erro no Caso de teste 05 - Imagem não foi salva");
        }

        ArrayList<Imagem> listaImagens = (ArrayList<Imagem>) this.repository.findAll();
        assertEquals(2, listaImagens.size());
    }

    /**
     * Função <b>findByCaminho(caminho)</b>
     * Deve encontrar uma imagem pelo caminho
     */
    @Test
    void CT06() {
        Imagem imagem = new Imagem("pasta/caminho.jpg");
        Optional<Imagem> imagemSalvaOptional = this.repository.save(imagem);

        if(imagemSalvaOptional.isEmpty()) {
            throw new Error("Erro no Caso de teste 02 - Imagem não foi salva");
        }

        Imagem imagemSalva = imagemSalvaOptional.get();

        Optional<Imagem> imagemRecuperada = this.repository.findByCaminho("pasta/caminho.jpg");
        assertTrue(imagemRecuperada.isPresent());
        assertEquals(imagemSalva.getId(), imagemRecuperada.get().getId());

    }

}