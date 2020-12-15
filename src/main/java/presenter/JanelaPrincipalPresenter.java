package presenter;

import business.TelaInicialBuilder;
import models.Notificacao;
import models.Usuario;
import presenter.states.JanelaPrincipalState;
import repository.NotificacaoRepository;
import views.JanelaPrincipal;

import javax.swing.*;
import java.util.List;

public class JanelaPrincipalPresenter extends AbstractPresenter{

    private JanelaPrincipalState state;
    private Usuario usuarioLogado;
    private NotificacaoRepository notificacaoRepository;
    private List<Notificacao> notificacaoList;

    public List<Notificacao> getNotificacaoList() {
        return notificacaoList;
    }

    public void setNotificacaoList(List<Notificacao> notificacaoList) {
        this.notificacaoList = notificacaoList;
    }

    public NotificacaoRepository getNotificacaoRepository() {
        return notificacaoRepository;
    }

    public JanelaPrincipalPresenter(boolean visible) {
        super(visible);
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.atualizarRodape(true);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        this.tela = new JanelaPrincipal();
        this.state = TelaInicialBuilder.getEstadoInicial(this);
        tela.setVisible(true);
        this.atualizarRodape(false);
    }

    @Override
    protected void adicionarListeners() {
        addCadastrarActionListener();
        addLogarActionListener();
        addDeslogarActionListener();
        addExibirTelaImagensListener();
        getConvertedView().getPermissoesMenuItem().addActionListener(e -> this.state.exibirTelaPermissoes());
        addExibirTelaUsuariosListener();
        getConvertedView().getBtnNotificacoes().addActionListener(e -> this.state.exibirNotificacoes());
    }

    @Override
    protected void iniciarRepositories() {
        notificacaoRepository = NotificacaoRepository.getInstance(false);
    }

    private void addCadastrarActionListener() {
        getConvertedView().getCadastrarMenuItem().addActionListener(e -> exibirCadastro());
    }

    private void addLogarActionListener() {
        getConvertedView().getLogarMenuItem().addActionListener(e -> exibirLogin());
    }

    private void addDeslogarActionListener() {
        getConvertedView().getDeslogarMenuItem().addActionListener(e -> deslogar());
    }

    private void addExibirTelaImagensListener() {
        getConvertedView().getAbrirImagensMenuItem().addActionListener(e -> exibirTelaImagens());
    }

    private void addExibirTelaUsuariosListener() {
        getConvertedView().getManterUsuariosMenuItem().addActionListener(e -> exibirTelaUsuarios());
    }

    public JanelaPrincipal getConvertedView() {
        return (JanelaPrincipal) this.tela;
    }

    private void exibirLogin(){
        this.state.exibirLogin();
    }

    private void exibirCadastro() {
        this.state.exibirTelaCadastro();
    }

    private void deslogar() {
        JOptionPane.showMessageDialog(this.tela, "Usuario deslogado com sucesso!", "Login", JOptionPane.INFORMATION_MESSAGE);
        this.state.deslogar();
        this.atualizarRodape(false);
    }

    private void exibirTelaImagens() {
        this.state.exibirTelaImagens();
    }

    private void exibirTelaUsuarios() {
        this.state.exibirTelaUsuarios();
    }


    public void setState(JanelaPrincipalState state) {
        this.state = state;
    }

    private void atualizarRodape(boolean logado) {
        if (logado) {
            this.getConvertedView().getUsuarioLogadoTitle().setText("Usuario Logado: ");
            this.getConvertedView().getCargoUsuarioTitle().setText("Cargo do Usuario: ");
            this.getConvertedView().getNotificacoesLabel().setText("Notificacoes: ");
            this.getConvertedView().getBtnNotificacoes().setVisible(true);

            this.getConvertedView().getUsuarioLogadoLabel().setText(usuarioLogado.getName());
            this.getConvertedView().getCargoUsuarioLabel().setText(usuarioLogado.getCargo().getNome());
        } else {
            this.getConvertedView().getUsuarioLogadoLabel().setText("");
            this.getConvertedView().getCargoUsuarioLabel().setText("");

            this.getConvertedView().getUsuarioLogadoTitle().setText("");
            this.getConvertedView().getCargoUsuarioTitle().setText("");
            this.getConvertedView().getNotificacoesLabel().setText("");
            this.getConvertedView().getBtnNotificacoes().setVisible(true);
        }

    }

}
