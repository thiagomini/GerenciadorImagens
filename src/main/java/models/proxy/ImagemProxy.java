package models.proxy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagemProxy implements ImagemInterface {
    private final String caminho;
    private ImagemReal imagemReal;

    public ImagemProxy(String caminho) {
        this.caminho = caminho;
    }

    public BufferedImage redimensionar(int tamanho) throws IOException {
        if (imagemReal == null) {
            imagemReal = new ImagemReal(this.caminho);
        }
        return this.imagemReal.redimensionar(tamanho);
    }

    @Override
    public int getAltura() throws IOException {
        if (imagemReal == null) {
            imagemReal = new ImagemReal(this.caminho);
        }
        return this.imagemReal.getAltura();
    }

    @Override
    public int getLargura() throws IOException {
        if (imagemReal == null) {
            imagemReal = new ImagemReal(this.caminho);
        }
        return this.imagemReal.getLargura();
    }

    public BufferedImage display() throws IOException {
       if (imagemReal == null) {
           imagemReal = new ImagemReal(this.caminho);
       }
       return imagemReal.display();
    }

    public String getNomeArquivo() {
        return caminho.substring(
                caminho.lastIndexOf(File.separator) + 1
        );
    }
}
