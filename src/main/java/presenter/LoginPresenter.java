package presenter;

import business.Autenticacao;
import views.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPresenter extends AbstractPresenter {
    private final Autenticacao autenticacao = new Autenticacao(false);

    public LoginPresenter(boolean visible) {
        super(visible);
    }


    @Override
    protected void iniciarTela(boolean visible) {
        this.tela = new LoginView();
        tela.setVisible(true);
    }

    @Override
    protected void adicionarListeners() {
        addLogarListener();
    }

    private void addLogarListener() {
        getConvertedView().getBtnLogar().addActionListener(e -> logar());
    }

    private void logar() {
        String email = getConvertedView().getTxtEmail().getText();
        String senha = String.valueOf(getConvertedView().getTxtSenha().getPassword());
        try {
            autenticacao.autenticar(email, senha);
            JOptionPane.showMessageDialog(this.tela, "Login realizado com sucesso!", "Login", JOptionPane.INFORMATION_MESSAGE);
            this.setChanged();
            this.notifyObservers();
            this.getTela().dispose();
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(this.tela, e.getMessage(), "Erro ao Logar", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LoginView getConvertedView() {
        return (LoginView) this.tela;
    }

}
