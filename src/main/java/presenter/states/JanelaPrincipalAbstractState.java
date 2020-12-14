package presenter.states;

import presenter.CadastroPresenter;
import presenter.JanelaPrincipalPresenter;
import presenter.LoginPresenter;

import java.util.Observable;
import java.util.Observer;

public class JanelaPrincipalAbstractState implements JanelaPrincipalState, Observer {

    protected JanelaPrincipalPresenter presenter;

    public JanelaPrincipalAbstractState(JanelaPrincipalPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void exibirLogin() {
        LoginPresenter loginPresenter = new LoginPresenter(true);
        loginPresenter.addObserver(this);
    }

    @Override
    public void exibirTelaCadastro() {
        CadastroPresenter cadastroPresenter = new CadastroPresenter(true);
        cadastroPresenter.addObserver(this);
    }

    @Override
    public void exibirTelaPermissoes() {

    }

    @Override
    public void deslogar() {

    }

    @Override
    public void exibirTelaImagens() {

    }

    @Override
    public void update(Observable o, Object arg) {
        JanelaPrincipalState state =
        o instanceof LoginPresenter
                ? new JanelaPrincipalLogadoState(presenter)
                : new JanelaPrincipalDeslogadoState(presenter);

        this.presenter.setState(state);
    }
}
