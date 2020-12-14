package presenter;

import business.TelaInicialBuilder;
import models.Usuario;
import presenter.states.JanelaPrincipalState;
import views.JanelaPrincipal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class JanelaPrincipalPresenter extends AbstractPresenter{

    private JanelaPrincipalState state;
    private Usuario usuarioLogado;

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
        try {
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(getClass().getResource("/imagens/small-placeholder.jpg")));
            this.getConvertedView().getBtnNotificacoes().setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void adicionarListeners() {
        addCadastrarActionListener();
        addLogarActionListener();
        addDeslogarActionListener();
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


    public void setState(JanelaPrincipalState state) {
        this.state = state;
    }

    private void atualizarRodape(boolean logado) {
        if (logado) {
            this.getConvertedView().getUsuarioLogadoTitle().setText("Usuario Logado: ");
            this.getConvertedView().getCargoUsuarioTitle().setText("Cargo do Usuario: ");

            this.getConvertedView().getUsuarioLogadoLabel().setText(usuarioLogado.getName());
            this.getConvertedView().getCargoUsuarioLabel().setText(usuarioLogado.getCargo().getNome());
        } else {
            this.getConvertedView().getUsuarioLogadoLabel().setText("");
            this.getConvertedView().getCargoUsuarioLabel().setText("");

            this.getConvertedView().getUsuarioLogadoTitle().setText("");
            this.getConvertedView().getCargoUsuarioTitle().setText("");
        }

    }

}
