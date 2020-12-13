package presenter.states;

import presenter.AbstractPresenter;
import presenter.JanelaPrincipalPresenter;
import presenter.LoginPresenter;

import javax.swing.*;

public class JanelaPrincipalDeslogadoState extends JanelaPrincipalAbstractState{

    public JanelaPrincipalDeslogadoState(JanelaPrincipalPresenter presenter) {
        super(presenter);
        presenter.getConvertedView().getAbrirImagensMenuItem().setEnabled(false);
        presenter.getConvertedView().getLogarMenuItem().setEnabled(true);
        presenter.getConvertedView().getLogarMenuItem().setVisible(true);

        presenter.getConvertedView().getCadastrarMenuItem().setEnabled(true);

        presenter.getConvertedView().getDeslogarMenuItem().setEnabled(false);
        presenter.getConvertedView().getDeslogarMenuItem().setVisible(false);
    }

    @Override
    public void exibirTelaPermissoes() {
        throw new IllegalStateException("Não é possível exibir as permissões nesse estado!");
    }

    @Override
    public void deslogar() {
        throw new IllegalStateException("Não é possível deslogar nesse estado!");
    }

    @Override
    public void exibirTelaImagens() {
        throw new IllegalStateException("Não é possível exibir as imagens nesse estado!");
    }

    private void mudarBotaoParaDeslogar() {
        JMenuItem menuItem = presenter.getConvertedView().getLogarMenuItem();
        menuItem.setText("deslogar");
    }
}