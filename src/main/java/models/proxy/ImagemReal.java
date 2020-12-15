package models.proxy;

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
        if (targetSize <= 0) {
            return imagemLida; //this can't be resized
        }
        int targetWidth = targetSize;
        int targetHeight = targetSize;
        float ratio = ((float) imagemLida.getHeight() / (float) imagemLida.getWidth());
        if (ratio <= 1) { //square or landscape-oriented image
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else { //portrait image
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, imagemLida.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(imagemLida, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bi;
    }
}
