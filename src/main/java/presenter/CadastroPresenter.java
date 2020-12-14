package presenter;

import factory.UsuarioFactory;
import views.CadastroView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroPresenter extends AbstractPresenter{

    UsuarioFactory usuarioFactory;

    public CadastroPresenter(boolean visible) {
        super(visible);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        this.tela = new CadastroView();
        this.usuarioFactory = new UsuarioFactory(false);
        this.tela.setVisible(visible);
    }

    @Override
    protected void adicionarListeners() {
        this.addNovoUsuarioActionListener();
    }

    private void addNovoUsuarioActionListener() {
        getConvertedView().getBtnCadastrar().addActionListener(e -> cadastrarUsuario());
    }

    private void cadastrarUsuario() {
        CadastroView telaConvertida = getConvertedView();

        String nome = telaConvertida.getTxtNome().getText();
        String email = telaConvertida.getTxtEmail().getText();
        String senha = String.valueOf(telaConvertida.getTxtSenha().getPassword());
        usuarioFactory.createUsuario(nome, email, senha);
        JOptionPane.showMessageDialog(this.tela, "Novo usuario de Email " + email + " cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        this.setChanged();
        this.notifyObservers();
        this.tela.dispose();
    }

    private CadastroView getConvertedView() {
        return (CadastroView) this.tela;
    }
}
