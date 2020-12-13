package presenter;

import javax.swing.*;

public abstract class AbstractPresenter {
    protected JFrame tela;

    protected abstract JFrame iniciarTela();

    protected abstract void adicionarListeners();

    protected AbstractPresenter() {
        this.tela = iniciarTela();
        adicionarListeners();
    }
}
