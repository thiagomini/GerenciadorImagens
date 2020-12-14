package models.proxy;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImagemProxyTest {

    /**
     * Função <b>display()</b>
     * Deve ler uma única vez a imagem do computador
     */
    @Test
    void CT059() throws IOException {
        ImagemProxy imagemProxy = new ImagemProxy("/imagens/small-placeholder.jpg");
        Image imagemLida1Vez = imagemProxy.display();
        Image imagemLida2Vez = imagemProxy.display();
        assertEquals(imagemLida1Vez, imagemLida2Vez);

    }

    /**
     * Função <b>getScaledImage()</b>
     * Deve retornar corretamente uma imagem redimensionada
     */
    @Test
    void CT060() throws IOException {
        ImagemProxy imagemProxy = new ImagemProxy("/imagens/small-placeholder.jpg");
        BufferedImage imagemRedimensionada = imagemProxy.redimensionar(50);
        assertEquals(35, imagemRedimensionada.getHeight());
        assertEquals(50, imagemRedimensionada.getWidth());
    }
}