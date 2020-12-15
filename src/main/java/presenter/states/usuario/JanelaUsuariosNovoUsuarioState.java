package presenter.states.usuario;

import presenter.UsuariosPresenter;

public class JanelaUsuariosNovoUsuarioState extends JanelaUsuariosAbstractState {
    public JanelaUsuariosNovoUsuarioState(UsuariosPresenter usuariosPresenter) {
        super(usuariosPresenter);
        this.usuariosPresenter.habilitarBotoes(false, true, false, false, true);
        this.usuariosPresenter.limparCampos();
        this.usuariosPresenter.habilitarCampos(true);
    }

    @Override
    public void executarBotaoSalvar() {
        // TODO salvar os dados do usuário no banco
        this.usuariosPresenter.setState(new JanelaUsuariosInicialState(this.usuariosPresenter));
    }

    @Override
    public void executarBotaoCancelar() {
        this.usuariosPresenter.setState(new JanelaUsuariosInicialState(this.usuariosPresenter));
    }
}
