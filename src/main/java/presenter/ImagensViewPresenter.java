package presenter;

import components.ImageListCellRenderer;
import models.proxy.ImagemProxy;
import repository.ImagemRepository;
import repository.PermissoesImagemRepository;
import repository.UsuarioRepository;
import views.ImagensView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImagensViewPresenter extends AbstractPresenter{

    private final UsuarioRepository usuarioRepository = UsuarioRepository.getInstance(false);
    private final ImagemRepository imagemRepository = ImagemRepository.getInstance(false);
    private final PermissoesImagemRepository permissoesImagemRepository = PermissoesImagemRepository.getInstance(false);
    private List<ImagemProxy> imagensNaPasta;
    private DefaultListModel listModel;

    public ImagensViewPresenter(boolean visible, List<File> imagensNaPasta) {
        super(visible);
        this.imagensNaPasta = imagensNaPasta
                .stream()
                .map(imagem -> new ImagemProxy(imagem.getPath()))
                .collect(Collectors.toList());

        recarregarImagens();
    }

    @Override
    protected void iniciarTela(boolean visible) {
        tela = new ImagensView();
        listModel = new DefaultListModel();
        getConvertedView().getListaImagens().setModel(listModel);
        getConvertedView().getListaImagens().setCellRenderer(
                new ImageListCellRenderer()
        );
        tela.setVisible(true);
    }

    private ImagensView getConvertedView() {
        return (ImagensView) tela;
    }

    @Override
    protected void adicionarListeners() {

    }

    private void recarregarImagens() {
        this.imagensNaPasta.forEach(imagem -> {
            try {
                ImageIcon imageIcon = new ImageIcon(imagem.redimensionar(50));
                JLabel label = new JLabel(imagem.getNomeArquivo(), imageIcon, JLabel.LEFT);
                JPanel imagemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                imagemPanel.add(label);
                listModel.addElement(imagemPanel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
