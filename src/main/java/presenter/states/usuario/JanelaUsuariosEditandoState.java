package presenter.states.usuario;

import presenter.UsuariosPresenter;

public class JanelaUsuariosEditandoState extends JanelaUsuariosAbstractState {
    public JanelaUsuariosEditandoState(UsuariosPresenter usuariosPresenter) {
        super(usuariosPresenter);
        this.usuariosPresenter.habilitarCampos(true);
        this.usuariosPresenter.habilitarBotoes(false, true, false, false, true);
    }

    @Override
    public void executarBotaoCancelar() {
        this.usuariosPresenter.setState(new JanelaUsuariosInicialState(this.usuariosPresenter));
    }

    @Override
    public void executarBotaoSalvar() {
        this.usuariosPresenter.editarUsuario();
        this.usuariosPresenter.setState(new JanelaUsuariosInicialState(this.usuariosPresenter));
    }
}
