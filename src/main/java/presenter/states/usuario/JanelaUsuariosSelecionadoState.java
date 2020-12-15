package presenter.states.usuario;

import presenter.UsuariosPresenter;

public class JanelaUsuariosSelecionadoState extends JanelaUsuariosAbstractState {
    public JanelaUsuariosSelecionadoState(UsuariosPresenter usuariosPresenter) {
        super(usuariosPresenter);
        this.usuariosPresenter.limparCampos();
        this.usuariosPresenter.preencherCampos();
        this.usuariosPresenter.habilitarCampos(false);
        this.usuariosPresenter.habilitarBotoes(false, true, true, true, false);
    }

    @Override
    public void executarBotaoCancelar() {
        this.usuariosPresenter.setState(new JanelaUsuariosInicialState(this.usuariosPresenter));
    }

    @Override
    public void executarBotaoExcluir() {
        this.usuariosPresenter.excluirUsuario();
        this.usuariosPresenter.setState(new JanelaUsuariosInicialState(this.usuariosPresenter));
    }

    @Override
    public void executarBotaoEditar() {
        this.usuariosPresenter.setState(new JanelaUsuariosEditandoState(this.usuariosPresenter));
    }

    @Override
    public void clicarNaTabela() {
        this.usuariosPresenter.setState(new JanelaUsuariosSelecionadoState(this.usuariosPresenter));
    }
}
