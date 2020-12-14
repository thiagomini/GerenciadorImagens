package presenter.states;

import presenter.JanelaPrincipalPresenter;

public class JanelaPrincipalLogadoState extends JanelaPrincipalAbstractState{

    public JanelaPrincipalLogadoState(JanelaPrincipalPresenter presenter) {
        super(presenter);
        presenter.getConvertedView().getAbrirImagensMenuItem().setEnabled(true);

        presenter.getConvertedView().getLogarMenuItem().setEnabled(false);
        presenter.getConvertedView().getLogarMenuItem().setVisible(false);

        presenter.getConvertedView().getCadastrarMenuItem().setEnabled(true);

        presenter.getConvertedView().getDeslogarMenuItem().setEnabled(true);
        presenter.getConvertedView().getDeslogarMenuItem().setVisible(true);
    }

    @Override
    public void exibirTelaPermissoes() {
        throw new IllegalStateException("Não é possível exibir as permissões nesse estado!");
    }

    @Override
    public void deslogar() {
        presenter.setState(new JanelaPrincipalDeslogadoState(presenter));
    }

    @Override
    public void exibirTelaImagens() {
        throw new IllegalStateException("Não é possível exibir as imagens nesse estado!");
    }
}
