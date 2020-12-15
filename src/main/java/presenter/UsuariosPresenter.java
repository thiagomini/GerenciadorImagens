package presenter;

import presenter.states.usuario.JanelaUsuariosInicialState;
import presenter.states.usuario.JanelaUsuariosState;
import views.ManterUsuarioView;

public class UsuariosPresenter extends AbstractPresenter {

    private JanelaUsuariosState state;

    public UsuariosPresenter(boolean visible) {
        super(visible);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        this.tela = new ManterUsuarioView();
        this.state = new JanelaUsuariosInicialState(this);
        this.tela.setVisible(true);
    }

    @Override
    protected void adicionarListeners() {
        adicionarBotaoNovoActionListener();
        adicionarBotaoSalvarActionListener();
        adicionarBotaoCancelarActionListener();
    }

    private void adicionarBotaoNovoActionListener() {
        getConvertedView().getBtnNovo().addActionListener(e -> executarBotaoNovo());
    }

    private void adicionarBotaoSalvarActionListener() {
        getConvertedView().getBtnSalvar().addActionListener(e -> executarBotaoSalvar());
    }

    private void adicionarBotaoCancelarActionListener() {
        getConvertedView().getBtnCancelar().addActionListener(e -> executarBotaoCancelar());
    }

    private void executarBotaoNovo() {
        this.state.executarBotaoNovo();
    }

    private void executarBotaoSalvar() {
        this.state.executarBotaoSalvar();
    }

    private void executarBotaoCancelar() {
        this.state.executarBotaoCancelar();
    }

    public void habilitarBotoes(boolean botaoNovo, boolean botaoCancelar, boolean botaoExcluir, boolean botaoEditar, boolean botaoSalvar) {
        getConvertedView().getBtnNovo().setEnabled(botaoNovo);
        getConvertedView().getBtnCancelar().setEnabled(botaoCancelar);
        getConvertedView().getBtnExcluir().setEnabled(botaoExcluir);
        getConvertedView().getBtnEditar().setEnabled(botaoEditar);
        getConvertedView().getBtnSalvar().setEnabled(botaoSalvar);
    }

    public void habilitarCampos(boolean habilitar) {
        this.getConvertedView().getTxtNome().setEnabled(habilitar);
        this.getConvertedView().getTxtEmail().setEnabled(habilitar);
        this.getConvertedView().getTxtSenha().setEnabled(habilitar);
        this.getConvertedView().getComboBoxCargo().setEnabled(habilitar);
    }

    public void limparCampos() {
        this.getConvertedView().getTxtNome().setText("");
        this.getConvertedView().getTxtEmail().setText("");
        this.getConvertedView().getTxtSenha().setText("");
        this.getConvertedView().getComboBoxCargo().setSelectedItem("");
    }

    public ManterUsuarioView getConvertedView() {
        return (ManterUsuarioView) tela;
    }

    public JanelaUsuariosState getState() {
        return state;
    }

    public void setState(JanelaUsuariosState state) {
        this.state = state;
    }
}
