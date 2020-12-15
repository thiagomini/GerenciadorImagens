package presenter;

import views.PermissoesView;

public class PermissoesPresenter extends AbstractPresenter{

    protected PermissoesPresenter(boolean visible) {
        super(visible);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        tela = new PermissoesView();
        tela.setVisible(true);
    }

    @Override
    protected void adicionarListeners() {

    }
}
