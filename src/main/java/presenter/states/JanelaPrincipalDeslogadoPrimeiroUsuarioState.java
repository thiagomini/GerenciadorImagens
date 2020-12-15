package presenter.states;

import presenter.JanelaPrincipalPresenter;

public class JanelaPrincipalDeslogadoPrimeiroUsuarioState extends JanelaPrincipalDeslogadoState {

    public JanelaPrincipalDeslogadoPrimeiroUsuarioState(JanelaPrincipalPresenter presenter) {
        super(presenter);
        presenter.getConvertedView().getAbrirImagensMenuItem().setEnabled(false);
        presenter.getConvertedView().getLogarMenuItem().setEnabled(false);
        presenter.getConvertedView().getLogarMenuItem().setVisible(true);

        presenter.getConvertedView().getCadastrarMenuItem().setEnabled(true);

        presenter.getConvertedView().getDeslogarMenuItem().setEnabled(false);
        presenter.getConvertedView().getDeslogarMenuItem().setVisible(false);
        presenter.getConvertedView().getPermissoesMenuItem().setEnabled(false);
    }

    @Override
    public void exibirLogin() {
        throw new IllegalStateException("Não é possível logar ainda, não existe nenhum cadastro!");
    }


}
