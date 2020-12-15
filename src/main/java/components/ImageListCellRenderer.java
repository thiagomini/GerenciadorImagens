package components;

import models.proxy.ImagemProxy;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageListCellRenderer extends JLabel implements ListCellRenderer{
    public Component getListCellRendererComponent(JList jlist,
                                                  Object value,
                                                  int cellIndex,
                                                  boolean isSelected,
                                                  boolean cellHasFocus)
    {
        Color background;
        Color foreground;
        ImagemProxy imagemProxy = (ImagemProxy) value;
        ImageIcon imageIcon = null;
        setOpaque(true);
        try {
            imageIcon = new ImageIcon(imagemProxy.redimensionar(50));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setText(imagemProxy.getNomeArquivo());
        setIcon(imageIcon);

        JList.DropLocation dropLocation = jlist.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == cellIndex) {

            background = Color.BLUE;
            foreground = Color.WHITE;

            // check if this cell is selected
        }
        else if (isSelected) {
            background = Color.GRAY;
            foreground = Color.WHITE;

        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }
        setBackground(background);
        setForeground(foreground);
        return this;
    }
}

