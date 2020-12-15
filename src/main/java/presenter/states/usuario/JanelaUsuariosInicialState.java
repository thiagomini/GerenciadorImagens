package presenter.states.usuario;

import presenter.UsuariosPresenter;

public class JanelaUsuariosInicialState extends JanelaUsuariosAbstractState {

    public JanelaUsuariosInicialState(UsuariosPresenter usuariosPresenter) {
        super(usuariosPresenter);
        this.usuariosPresenter.limparCampos();
        this.usuariosPresenter.habilitarCampos(false);
        this.usuariosPresenter.habilitarBotoes(true, false, false, false, false);
        this.usuariosPresenter.reloadTabelaUsuarios();
    }

    @Override
    public void executarBotaoNovo() {
        this.usuariosPresenter.setState(new JanelaUsuariosNovoUsuarioState(this.usuariosPresenter));
    }
}
