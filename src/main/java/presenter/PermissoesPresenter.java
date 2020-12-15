package presenter;

import models.Imagem;
import models.Usuario;
import models.proxy.ImagemProxy;
import repository.ImagemRepository;
import repository.PermissoesImagemRepository;
import repository.UsuarioRepository;
import views.PermissoesView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

public class PermissoesPresenter extends AbstractPresenter{
    private UsuarioRepository usuarioRepository;
    private PermissoesImagemRepository permissoesImagemRepository;
    private ImagemRepository imagemRepository;
    private Imagem imagemSelecionada;

    protected PermissoesPresenter(boolean visible) {
        super(visible);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        tela = new PermissoesView();
        tela.setVisible(true);
        preencherComboBox();
        carregarPlaceHolder();
    }

    @Override
    protected void adicionarListeners() {

    }

    @Override
    protected void iniciarRepositories() {
        usuarioRepository = UsuarioRepository.getInstance(false);
        permissoesImagemRepository = PermissoesImagemRepository.getInstance(false);
        imagemRepository = ImagemRepository.getInstance(false);
    }

    private PermissoesView getConvertedView() {
        return (PermissoesView) tela;
    }

    private void preencherComboBox() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        JComboBox<Usuario> comboBox = getConvertedView().getUsuariosComboBox();
        listaUsuarios.forEach(comboBox::addItem);
    }

    public void carregarPlaceHolder() {
        int alturaLabel = getConvertedView().getPreviewLabel().getHeight();
        int larguraLabel = getConvertedView().getPreviewLabel().getWidth();

        ImageIcon placeHolder = new ImageIcon(getClass().getResource("/imagens/big-placeholder.png"));
        Image imagem = placeHolder.getImage();
        Image imagemRedimensionada = imagem.getScaledInstance(larguraLabel, alturaLabel, java.awt.Image.SCALE_SMOOTH);
        getConvertedView().getPreviewLabel().setIcon(new ImageIcon(imagemRedimensionada));
    }

    private void botaoEscolherImagem() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG Files", "jpg", "jpeg");
        j.setFileFilter(filter);
        int resultado = j.showOpenDialog(tela);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String caminho = j.getSelectedFile().getPath();
            try {
                Optional<Imagem> imagemEncontrada = imagemRepository.findByCaminho(caminho);
                Imagem imagem = imagemEncontrada.orElse(new Imagem(caminho));
                imagem.setImagemProxy(new ImagemProxy(caminho));
                imagemSelecionada = imagem;

                atualizaImagem(imagem.getImagemProxy().redimensionar(getConvertedView().getPreviewLabel().getWidth()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, ex.getMessage());
            }

        }
    }

    private void atualizaImagem(BufferedImage imagem) {
        getConvertedView().getPreviewLabel().setIcon(new ImageIcon(imagem));
    }
}
