package presenter;

import models.Cargo;
import models.Usuario;
import presenter.states.usuario.JanelaUsuariosInicialState;
import presenter.states.usuario.JanelaUsuariosState;
import repository.CargoRepository;
import repository.UsuarioRepository;
import views.ManterUsuarioView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;

public class UsuariosPresenter extends AbstractPresenter {

    private JanelaUsuariosState state;
    private UsuarioRepository usuarioRepository;
    private CargoRepository cargoRepository;
    private DefaultTableModel defaultTableModel;
    private Usuario usuarioSelecionado;

    public UsuariosPresenter(boolean visible) {
        super(visible);
    }

    @Override
    protected void iniciarRepositories() {
        cargoRepository = CargoRepository.getInstance(false);
        usuarioRepository = UsuarioRepository.getInstance(false);
    }

    @Override
    protected void iniciarTela(boolean visible) {
        this.tela = new ManterUsuarioView();
        listarCargos();
        defaultTableModel = (DefaultTableModel) getConvertedView().getTableUsuarios().getModel();
        this.state = new JanelaUsuariosInicialState(this);
        this.tela.setVisible(true);
    }

    @Override
    protected void adicionarListeners() {
        adicionarBotaoNovoActionListener();
        adicionarBotaoSalvarActionListener();
        adicionarBotaoCancelarActionListener();
        adicionarBotaoExcluirActionListener();
        adicionarBotaoEditarActionListener();
        clicarNaTabelaListSelectionListener();
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

    private void adicionarBotaoExcluirActionListener() {
        getConvertedView().getBtnExcluir().addActionListener(e -> executarBotaoExcluir());
    }

    private void adicionarBotaoEditarActionListener() {
        getConvertedView().getBtnEditar().addActionListener(e -> executarBotaoEditar());
    }

    private void clicarNaTabelaListSelectionListener() {
        JTable tableUsuarios = getConvertedView().getTableUsuarios();
        tableUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();
                int linhaSelecionada = tableUsuarios.getSelectedRow();
                if (linhaSelecionada == -1) return;
                Long idUsuarioSelecionado = (Long) model.getValueAt(linhaSelecionada, 0);
                Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuarioSelecionado);
                usuarioSelecionado = usuarioOptional.get();
                clicarNaTabela();
            }
        });
    }

    public void preencherCampos() {
        getConvertedView().getTxtNome().setText(usuarioSelecionado.getName());
        getConvertedView().getTxtEmail().setText(usuarioSelecionado.getEmail());
        getConvertedView().getTxtSenha().setText(usuarioSelecionado.getPassword());
        // FIXME não está exibindo o cargo corretamente no comboBox para o usuário selecionado
        getConvertedView().getComboBoxCargo().setSelectedItem(usuarioSelecionado.getCargo());
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

    private void executarBotaoExcluir() {
        this.state.executarBotaoExcluir();
    }

    private void executarBotaoEditar() {
        this.state.executarBotaoEditar();
    }

    private void clicarNaTabela() {
        this.state.clicarNaTabela();
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

    public void salvarUsuario() {
        String nome = this.getConvertedView().getTxtNome().getText();
        String email = this.getConvertedView().getTxtEmail().getText();
        String senha = this.getConvertedView().getTxtSenha().getText();
        Cargo cargo = (Cargo) this.getConvertedView().getComboBoxCargo().getSelectedItem();
        Usuario usuario = new Usuario(nome, email, senha, cargo);
        usuarioRepository.update(usuario);
    }

    public void editarUsuario() {
        String nome = this.getConvertedView().getTxtNome().getText();
        String email = this.getConvertedView().getTxtEmail().getText();
        String senha = this.getConvertedView().getTxtSenha().getText();
        Cargo cargo = (Cargo) this.getConvertedView().getComboBoxCargo().getSelectedItem();
        usuarioSelecionado.setName(nome);
        usuarioSelecionado.setEmail(email);
        usuarioSelecionado.setPassword(senha);
        usuarioSelecionado.setCargo(cargo);
        usuarioRepository.update(usuarioSelecionado);
    }

    public void excluirUsuario() {
        this.usuarioRepository.delete(usuarioSelecionado);
    }

    public void listarCargos() {
        ArrayList<Cargo> cargos = (ArrayList<Cargo>) this.cargoRepository.findAll();
        cargos.forEach(c -> this.getConvertedView().getComboBoxCargo().addItem(c));
    }

    public void reloadTabelaUsuarios() {
        defaultTableModel.setNumRows(0);
        ArrayList<Usuario> usuarios = (ArrayList<Usuario>) this.usuarioRepository.findAll();
        usuarios.forEach(u -> defaultTableModel.addRow(new Object[]{u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getCargo()}));
    }

    public ManterUsuarioView getConvertedView() {
        return (ManterUsuarioView) tela;
    }

    public void setState(JanelaUsuariosState state) {
        this.state = state;
    }
}
