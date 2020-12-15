package image_tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageToolkit {

    private ImageToolkit(){}

    public static BufferedImage resizeImage(BufferedImage imagem, int tamanho) {
        if (tamanho <= 0) {
            return imagem; //this can't be resized
        }
        int targetWidth = tamanho;
        int targetHeight = tamanho;
        float ratio = ((float) imagem.getHeight() / (float) imagem.getWidth());
        if (ratio <= 1) { //square or landscape-oriented image
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else { //portrait image
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, imagem.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(imagem, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bi;
    }
}
