package models.proxy;

import image_tools.ImageToolkit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImagemReal implements ImagemInterface {
    private final BufferedImage imagemLida;

    public ImagemReal(String fileName) throws IOException {
        this.imagemLida = ImageIO.read(new File(fileName));
    }

    public ImagemReal(URL resource) throws IOException {
        this.imagemLida = ImageIO.read(resource);
    }

    public ImagemReal(File imagem) throws IOException {
        this.imagemLida = ImageIO.read(imagem);
    }

    @Override
    public BufferedImage display() {
        return this.imagemLida;
    }

    public BufferedImage redimensionar(int tamanho) {
        return this.resize(tamanho);
    }

    @Override
    public int getAltura() {
        return this.imagemLida.getHeight();
    }

    @Override
    public int getLargura() {
        return this.imagemLida.getWidth();
    }

    private BufferedImage resize(int targetSize) {
        return ImageToolkit.resizeImage(this.imagemLida, targetSize);
    }
}
