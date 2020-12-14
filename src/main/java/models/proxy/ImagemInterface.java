package models.proxy;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImagemInterface {
    public BufferedImage display() throws IOException;

    public BufferedImage redimensionar(int tamanho) throws IOException;

    public int getAltura() throws IOException;

    public int getLargura() throws IOException;
}
