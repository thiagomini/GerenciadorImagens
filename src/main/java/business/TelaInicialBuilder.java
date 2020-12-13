package business;

import presenter.AbstractPresenter;
import presenter.CadastroPresenter;
import presenter.JanelaPrincipalPresenter;
import presenter.LoginPresenter;
import presenter.states.JanelaPrincipalDeslogadoPrimeiroUsuarioState;
import presenter.states.JanelaPrincipalDeslogadoState;
import presenter.states.JanelaPrincipalState;
import repository.UsuarioRepository;
import views.JanelaPrincipal;

import javax.swing.*;

public class TelaInicialBuilder {
    private static final UsuarioRepository repository = UsuarioRepository.getInstance(false);
    private TelaInicialBuilder() {

    }

    public static JanelaPrincipalState getEstadoInicial(AbstractPresenter presenter) {
        return repository.hasOneRegistered()
                ? new JanelaPrincipalDeslogadoState((JanelaPrincipalPresenter) presenter)
                : new JanelaPrincipalDeslogadoPrimeiroUsuarioState((JanelaPrincipalPresenter) presenter);
    }

}
