package business;

import presenter.AbstractPresenter;
import presenter.CadastroPresenter;
import presenter.LoginPresenter;
import repository.UsuarioRepository;

public class TelaInicialBuilder {
    private static final UsuarioRepository repository = UsuarioRepository.getInstance();
    private TelaInicialBuilder() {

    }

    public static AbstractPresenter getTelaInicial() {
        return repository.hasOneRegistered()
                ? new LoginPresenter()
                : new CadastroPresenter();
    }

}
