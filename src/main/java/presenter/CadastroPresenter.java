package presenter;

import factory.UsuarioFactory;
import models.Usuario;
import views.CadastroView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroPresenter extends AbstractPresenter{

    UsuarioFactory usuarioFactory;

    @Override
    protected JFrame iniciarTela() {
        this.tela = new CadastroView();
        this.usuarioFactory = new UsuarioFactory(false);
        this.tela.setVisible(true);
        return this.tela;
    }

    @Override
    protected void adicionarListeners() {
        this.addNovoUsuarioActionListener();
    }

    private void addNovoUsuarioActionListener() {
        getConvertedView().getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        CadastroView telaConvertida = getConvertedView();

        String nome = telaConvertida.getTxtNome().getText();
        String email = telaConvertida.getTxtEmail().getText();
        String senha = String.valueOf(telaConvertida.getTxtSenha().getPassword());
        Usuario usuarioCriado = usuarioFactory.createUsuario(nome, email, senha);
        JOptionPane.showMessageDialog(this.tela, "Novo usuário de Email " + email + " cadastrado com sucesso!");
    }

    private CadastroView getConvertedView() {
        return (CadastroView) this.tela;
    }
}
