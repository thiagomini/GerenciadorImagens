package models.proxy;

import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ImagemRealTest {

    /**
     * Função <b>display()</b>
     * Deve ler uma imagem do disco
     */
    @Test
    void CT057() throws IOException {
        ImagemReal imagemReal = new ImagemReal(getClass().getResource("/imagens/small-placeholder.jpg"));
        assertNotNull(imagemReal.display());
    }

    /**
     * Função <b>redimensionar(tamanho)</b>
     * Deve retornar corretamente uma imagem redimensionada de tamanho 280 x 192
     */
    @Test
    void CT058() throws IOException {
        ImagemReal imagemReal = new ImagemReal("/imagens/small-placeholder.jpg");
        BufferedImage imagemRedimensionada = imagemReal.redimensionar(50);
        assertEquals(35, imagemRedimensionada.getHeight());
        assertEquals(50, imagemRedimensionada.getWidth());
    }
}