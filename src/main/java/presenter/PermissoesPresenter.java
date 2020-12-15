package presenter;

import models.Imagem;
import models.PermissaoImagem;
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
import java.util.stream.Collectors;

public class PermissoesPresenter extends AbstractPresenter{
    private UsuarioRepository usuarioRepository;
    private PermissoesImagemRepository permissoesImagemRepository;
    private ImagemRepository imagemRepository;
    private Imagem imagemSelecionada;
    private Usuario usuarioSelecionado;

    public PermissoesPresenter(boolean visible) {
        super(visible);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        tela = new PermissoesView();
        tela.setVisible(true);
        preencherComboBox();
        carregarPlaceHolder();
        habilitarPermissoes(false);
        getConvertedView().getBtnSalvar().setEnabled(false);
    }

    @Override
    protected void adicionarListeners() {
        getConvertedView().getBtnEscolherImagem().addActionListener(e -> botaoEscolherImagem());
        getConvertedView().getUsuariosComboBox().addActionListener(e -> carregaPermissoes());
        getConvertedView().getBtnFechar().addActionListener(e -> tela.dispose());
        getConvertedView().getBtnSalvar().addActionListener(e -> salvarPermissoes());

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

    private void habilitarPermissoes(boolean habilitado) {
        getConvertedView().getUsuariosComboBox().setEnabled(habilitado);
        getConvertedView().getBtnLeitura().setEnabled(habilitado);
        getConvertedView().getBtnExclusao().setEnabled(habilitado);
        getConvertedView().getBtnCompartilhamento().setEnabled(habilitado);
    }

    private void preencherComboBox() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll().stream().filter(
                usuario -> !usuario.getCargo().getCode().equals("admin")
        ).collect(Collectors.toList());

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

                if (imagemEncontrada.isEmpty()) {
                    imagemEncontrada = imagemRepository.save(new Imagem(caminho));
                }

                if (imagemEncontrada.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            tela,
                            "Erro ao Carregar a imagem!",
                            "Carregar Imagem",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                imagemSelecionada = imagemEncontrada.get();
                imagemSelecionada.setImagemProxy(new ImagemProxy(caminho));
                getConvertedView().getCaminhoImagemTxtField().setText(imagemSelecionada.getImagemProxy().getNomeArquivo());
                atualizaImagem(imagemSelecionada.getImagemProxy().redimensionar(getConvertedView().getPreviewLabel().getWidth()));
                habilitarPermissoes(true);
                carregaPermissoes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, ex.getMessage());
            }

        }
    }

    private void atualizaImagem(BufferedImage imagem) {
        getConvertedView().getPreviewLabel().setIcon(new ImageIcon(imagem));
    }

    private void setaBotoesPermissao(boolean btnLeitura, boolean btnExclusao, boolean btnCompartilhamento) {
        getConvertedView().getBtnLeitura().setSelected(btnLeitura);
        getConvertedView().getBtnExclusao().setSelected(btnExclusao);
        getConvertedView().getBtnCompartilhamento().setSelected(btnCompartilhamento);
    }

    private void carregaPermissoes() {
        this.usuarioSelecionado = (Usuario) getConvertedView().getUsuariosComboBox().getSelectedItem();
        assert usuarioSelecionado != null;
        Optional<PermissaoImagem> permissaoImagem = permissoesImagemRepository.findByUserAndImage(
                usuarioSelecionado.getId(),
                imagemSelecionada.getId()
        );

        permissaoImagem.ifPresent(imagem -> setaBotoesPermissao(
                imagem.isVisualizacao(),
                imagem.isExclusao(),
                imagem.isCompartilhamento()
        ));

        getConvertedView().getBtnSalvar().setEnabled(true);
    }

    private void salvarPermissoes() {
        boolean visualizacao = getConvertedView().getBtnLeitura().isSelected();
        boolean exclusao = getConvertedView().getBtnExclusao().isSelected();
        boolean compartilhamento = getConvertedView().getBtnCompartilhamento().isSelected();

        PermissaoImagem novaPermissao = new PermissaoImagem(
                usuarioSelecionado,
                imagemSelecionada,
                visualizacao,
                exclusao,
                compartilhamento
        );

        permissoesImagemRepository.update(novaPermissao);
        JOptionPane.showMessageDialog(
                tela,
                "Permissoes atualizadas com sucesso!",
                "Permissoes",
                JOptionPane.INFORMATION_MESSAGE
        );
        resetaTela();
    }

    private void resetaTela() {
        setaBotoesPermissao(false, false, false);
        carregaPermissoes();
        getConvertedView().getCaminhoImagemTxtField().setText("");
        usuarioSelecionado = null;
        imagemSelecionada = null;
        carregarPlaceHolder();
    }
}
