package presenter.states;

import presenter.JanelaPrincipalPresenter;

public class JanelaPrincipalLogadoState extends JanelaPrincipalAbstractState{

    public JanelaPrincipalLogadoState(JanelaPrincipalPresenter presenter) {
        super(presenter);
        presenter.getConvertedView().getAbrirImagensMenuItem().setEnabled(true);
        presenter.getConvertedView().getPermissoesMenuItem().setEnabled(true);
        presenter.getConvertedView().getManterUsuariosMenuItem().setEnabled(true);
        presenter.getConvertedView().getCadastrarMenuItem().setEnabled(false);
        presenter.getConvertedView().getBtnNotificacoes().setEnabled(true);

        presenter.getConvertedView().getLogarMenuItem().setEnabled(false);
        presenter.getConvertedView().getLogarMenuItem().setVisible(false);

        presenter.getConvertedView().getDeslogarMenuItem().setEnabled(true);
        presenter.getConvertedView().getDeslogarMenuItem().setVisible(true);
    }


    @Override
    public void exibirTelaCadastro() {
        throw new IllegalStateException("Não é possível cadastrar enquanto está logado!");
    }

}
