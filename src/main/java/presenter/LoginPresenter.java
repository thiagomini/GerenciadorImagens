package presenter;

import business.Autenticacao;
import models.Usuario;
import views.LoginView;

import javax.swing.*;

public class LoginPresenter extends AbstractPresenter {
    private final Autenticacao autenticacao = new Autenticacao(false);
    private Usuario usuarioLogado;

    public LoginPresenter(boolean visible) {
        super(visible);
    }


    @Override
    protected void iniciarTela(boolean visible) {
        this.tela = new LoginView();
        tela.setVisible(true);
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    @Override
    protected void adicionarListeners() {
        addLogarListener();
    }

    @Override
    protected void iniciarRepositories() {

    }

    private void addLogarListener() {
        getConvertedView().getBtnLogar().addActionListener(e -> logar());
    }

    private void logar() {
        String email = getConvertedView().getTxtEmail().getText();
        String senha = String.valueOf(getConvertedView().getTxtSenha().getPassword());
        try {
            this.usuarioLogado = autenticacao.autenticar(email, senha);
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
