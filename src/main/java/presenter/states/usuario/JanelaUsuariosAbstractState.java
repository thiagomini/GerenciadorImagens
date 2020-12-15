package presenter.states.usuario;

import presenter.UsuariosPresenter;

public class JanelaUsuariosAbstractState implements JanelaUsuariosState {

    protected UsuariosPresenter usuariosPresenter;

    public JanelaUsuariosAbstractState(UsuariosPresenter usuariosPresenter) {
        this.usuariosPresenter = usuariosPresenter;
    }

    @Override
    public void executarBotaoNovo() {
        throw new IllegalStateException();
    }

    @Override
    public void executarBotaoEditar() {
        throw new IllegalStateException();
    }

    @Override
    public void executarBotaoExcluir() {
        throw new IllegalStateException();
    }

    @Override
    public void executarBotaoCancelar() {
        throw new IllegalStateException();
    }

    @Override
    public void executarBotaoSalvar() {
        throw new IllegalStateException();
    }

    @Override
    public void clicarNaTabela() {
        throw new IllegalStateException();
    }
}
