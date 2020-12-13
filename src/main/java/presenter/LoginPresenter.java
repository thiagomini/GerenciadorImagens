package presenter;

import views.LoginView;

import javax.swing.*;

public class LoginPresenter extends AbstractPresenter {
    @Override
    protected JFrame iniciarTela() {
        this.tela = new LoginView();
        this.tela.setVisible(true);
        return this.tela;
    }

    @Override
    protected void adicionarListeners() {
           
    }
}
