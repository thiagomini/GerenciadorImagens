package presenter.states;

import presenter.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.*;

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

    }

    @Override
    public void deslogar() {
        presenter.setState(new JanelaPrincipalDeslogadoState(presenter));
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
            JOptionPane.showMessageDialog(null,
                    "Somente o administrador pode ter acesso à tela de usuários");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        JanelaPrincipalState state;
        if (o instanceof LoginPresenter) {
            state = new JanelaPrincipalLogadoState(presenter);
            presenter.setUsuarioLogado(((LoginPresenter) o).getUsuarioLogado());
        } else {
            state = new JanelaPrincipalDeslogadoState(presenter);
        }

        this.presenter.setState(state);
    }
}
