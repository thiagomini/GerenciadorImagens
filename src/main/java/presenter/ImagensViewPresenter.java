package presenter;

import business.PermissaoHandler;
import components.ImageListCellRenderer;
import models.Imagem;
import models.Usuario;
import models.proxy.ImagemProxy;
import presenter.states.imagens.ImagemViewInitialState;
import presenter.states.imagens.ImagemViewState;
import repository.ImagemRepository;
import repository.NotificacaoRepository;
import repository.PermissoesImagemRepository;
import repository.UsuarioRepository;
import views.ImagensView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImagensViewPresenter extends AbstractPresenter {

    private UsuarioRepository usuarioRepository;
    private ImagemRepository imagemRepository;
    private PermissoesImagemRepository permissoesImagemRepository;
    private NotificacaoRepository notificacaoRepository;
    private List<ImagemProxy> imagensNaPasta;
    private DefaultListModel listModel;
    private ImagemProxy imagemSelecionada;
    private ImagemViewState state;
    private PermissaoHandler permissaoHandler = new PermissaoHandler(false);
    private Usuario usuarioLogado;

    public void setState(ImagemViewState state) {
        this.state = state;
    }

    public ImagemProxy getImagemSelecionada() {
        return imagemSelecionada;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public NotificacaoRepository getNotificacaoRepository() {
        return notificacaoRepository;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public ImagemRepository getImagemRepository() {
        return imagemRepository;
    }

    public PermissoesImagemRepository getPermissoesImagemRepository() {
        return permissoesImagemRepository;
    }

    public void setImagemSelecionada(ImagemProxy imagemSelecionada) {
        this.imagemSelecionada = imagemSelecionada;
    }

    public ImagensViewPresenter(boolean visible, List<File> imagensNaPasta, Usuario usuarioLogado) {
        super(visible);
        this.imagensNaPasta = imagensNaPasta
                .stream()
                .map(imagem -> new ImagemProxy(imagem.getPath()))
                .collect(Collectors.toList());

        recarregarImagens();
        this.usuarioLogado = usuarioLogado;
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
        state = new ImagemViewInitialState(this);
        carregarPlaceHolder();
    }

    public ImagensView getConvertedView() {
        return (ImagensView) tela;
    }

    @Override
    protected void adicionarListeners() {
        addCliqueListaListener();
        getConvertedView().getBtnFechar().addActionListener(e -> tela.dispose());
        getConvertedView().getBtnSolicitarPermissao().addActionListener(e -> state.solicitarPermissao());
    }

    @Override
    protected void iniciarRepositories() {
        usuarioRepository = UsuarioRepository.getInstance(false);
        imagemRepository = ImagemRepository.getInstance(false);
        permissoesImagemRepository = PermissoesImagemRepository.getInstance(false);
        notificacaoRepository = NotificacaoRepository.getInstance(false);
    }

    private void selecionarItemLista() {
        this.state.selecionarItemLista();
    }

    private void addCliqueListaListener() {
        getConvertedView().getListaImagens().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarItemLista();
            }
        });
    }

    private void recarregarImagens() {
        this.imagensNaPasta.forEach(imagem ->
                listModel.addElement(imagem)
        );
    }

    public boolean temPermissaoParaVisualizar(Imagem imagem) {
        return permissaoHandler.temPermissaoParaVisualizar(
                usuarioLogado,
                imagem
        );
    }

    public void carregarPlaceHolder() {
        int alturaLabel = getConvertedView().getImagemLabel().getHeight();
        int larguraLabel = getConvertedView().getImagemLabel().getWidth();

        ImageIcon placeHolder = new ImageIcon(getClass().getResource("/imagens/big-placeholder.png"));
        Image imagem = placeHolder.getImage();
        Image imagemRedimensionada = imagem.getScaledInstance(larguraLabel, alturaLabel, java.awt.Image.SCALE_SMOOTH);
        getConvertedView().getImagemLabel().setIcon(new ImageIcon(imagemRedimensionada));
    }

    public void setBotoes(
            boolean compartilhar,
            boolean solicitarPermissoes,
            boolean excluir,
            boolean fechar
    ) {
        getConvertedView().getBtnCompartilhar().setEnabled(compartilhar);
        getConvertedView().getBtnSolicitarPermissao().setEnabled(solicitarPermissoes);
        getConvertedView().getBtnExcluir().setEnabled(excluir);
        getConvertedView().getBtnFechar().setEnabled(fechar);
    }

}
