package presenter;

import business.TelaInicialBuilder;
import presenter.states.JanelaPrincipalState;
import views.JanelaPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipalPresenter extends AbstractPresenter{

    private JanelaPrincipalState state;

    public JanelaPrincipalPresenter(boolean visible) {
        super(visible);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        this.tela = new JanelaPrincipal();
        this.state = TelaInicialBuilder.getEstadoInicial(this);
        tela.setVisible(true);
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
    }


    public void setState(JanelaPrincipalState state) {
        this.state = state;
    }

}
