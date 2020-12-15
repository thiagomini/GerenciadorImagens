package models.proxy;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImagemProxyTest {
    /**
     * Função <b>getNomeArquivo()</b>
     * Deve retornar corretamente o nome do arquivo
     */
    @Test
    void CT061() {
        ImagemProxy imagemProxy = new ImagemProxy("\\imagens\\small-placeholder.jpg");
        assertEquals("small-placeholder.jpg", imagemProxy.getNomeArquivo());
    }
}