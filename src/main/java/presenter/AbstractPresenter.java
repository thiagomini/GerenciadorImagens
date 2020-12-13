package presenter;

import javax.swing.*;
import java.util.Observable;

public abstract class AbstractPresenter extends Observable {
    protected JFrame tela;

    protected abstract void iniciarTela(boolean visible);

    protected abstract void adicionarListeners();

    protected AbstractPresenter(boolean visible) {
        iniciarTela(visible);
        adicionarListeners();
    }

    public JFrame getTela() {
        return tela;
    }

    public void closeView() {
        this.tela.dispose();
    }
}
