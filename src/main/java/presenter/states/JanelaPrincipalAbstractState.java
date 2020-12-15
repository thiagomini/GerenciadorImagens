package presenter.states;

import models.Notificacao;
import presenter.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
        if (presenter.getUsuarioLogado().getCargo().getCode().equals("admin")) {
            new PermissoesPresenter(true);
        } else {
            JOptionPane.showMessageDialog(
                    presenter.getTela(),
                    "Apena admnistradores podem visualizar as permissoes!",
                    "Permissoes",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    @Override
    public void deslogar() {
        presenter.setState(new JanelaPrincipalDeslogadoState(presenter));
        presenter.getConvertedView().getBtnNotificacoes().setCursor(
                new Cursor(Cursor.DEFAULT_CURSOR)
        );
        presenter.getConvertedView().getBtnNotificacoes().setText("0");
        presenter.setNotificacaoList(new ArrayList<>());
    }

    @Override
    public void exibirTelaImagens() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.setAcceptAllFileFilterUsed(false);
        j.setCurrentDirectory(new java.io.File("."));
        int resultado = j.showOpenDialog(presenter.getTela());
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File pasta = j.getSelectedFile();
            List<File> arquivos = new ArrayList<>(Arrays.asList(
                    Objects.requireNonNull(pasta.listFiles((dir, name) ->
                            name.toLowerCase().endsWith(".jpg")
                                    || name.toLowerCase().endsWith(".jpeg")
                                    || name.toLowerCase().endsWith(".png")))
            ));

            new ImagensViewPresenter(true, arquivos, presenter.getUsuarioLogado());

        }
    }

    @Override
    public void exibirTelaUsuarios() {
        if (this.presenter.getUsuarioLogado().getCargo().getCode().equals("admin")) {
            new UsuariosPresenter(true);
        } else {
            JOptionPane.showMessageDialog(presenter.getTela(),
                    "Somente o administrador pode ter acesso a tela de usuarios",
                    "Permissoes",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void exibirNotificacoes() {

        String notificacoes = "";
        List<Notificacao> listaNaoLidos = presenter
                .getNotificacaoList()
                .stream()
                .filter(notificacao -> !notificacao.isLida())
                .collect(Collectors.toList());

        for (Notificacao n: listaNaoLidos) {
            notificacoes = notificacoes.concat(n.getDescricao() + "\n");
            n.setLida(true);
            presenter.getNotificacaoRepository().merge(n);
        }

        JOptionPane.showMessageDialog(
                presenter.getTela(),
                notificacoes,
                "Notificacoes",
                JOptionPane.INFORMATION_MESSAGE
        );

        presenter.getNotificacaoList().clear();
        carregarNotificacoes();
    }

    @Override
    public void update(Observable o, Object arg) {
        JanelaPrincipalState state;
        if (o instanceof LoginPresenter) {
            state = new JanelaPrincipalLogadoState(presenter);
            presenter.setUsuarioLogado(((LoginPresenter) o).getUsuarioLogado());
            carregarNotificacoes();
        } else {
            state = new JanelaPrincipalDeslogadoState(presenter);
        }

        this.presenter.setState(state);
    }

    private void carregarNotificacoes() {
        presenter.setNotificacaoList(
                presenter.getNotificacaoRepository().findByUser(
                        presenter.getUsuarioLogado().getId()
                )
                        .stream()
                        .filter(notificacao -> !notificacao.isLida())
                        .collect(Collectors.toList())
        );
        presenter.getConvertedView().getBtnNotificacoes().setText(
                String.valueOf(presenter.getNotificacaoList().size())
        );

        if (!presenter.getNotificacaoList().isEmpty()) {
            presenter.getConvertedView().getBtnNotificacoes().setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );
            presenter.getConvertedView().getBtnNotificacoes().setEnabled(true);
        } else {
            presenter.getConvertedView().getBtnNotificacoes().setCursor(
                    new Cursor(Cursor.DEFAULT_CURSOR)
            );
            presenter.getConvertedView().getBtnNotificacoes().setEnabled(false);
        }
    }


}
