package presenter.states.imagens;

import models.Imagem;
import models.Notificacao;
import models.Usuario;
import models.proxy.ImagemProxy;
import presenter.ImagensViewPresenter;
import repository.NotificacaoRepository;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImagemViewAbstractState implements ImagemViewState{

    private ImagensViewPresenter presenter;

    public ImagemViewAbstractState(ImagensViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void selecionarItemLista() {
        ImagemProxy imagemSelecionada = presenter.getConvertedView().getListaImagens().getSelectedValue();
        Optional<Imagem> optionalImagem = presenter.getImagemRepository().findByCaminho(imagemSelecionada.getCaminho());
        boolean temPermissao = presenter.temPermissaoParaVisualizar(
                optionalImagem.orElse(new Imagem(imagemSelecionada.getCaminho()))
        );

        if (temPermissao) {
            try {
                int novoTamanho = presenter.getConvertedView().getImagemLabel().getWidth();
                this.presenter.getConvertedView().getImagemLabel().setIcon(
                        new ImageIcon(imagemSelecionada.redimensionar(novoTamanho))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            presenter.carregarPlaceHolder();
            JOptionPane.showMessageDialog(
                    presenter.getTela(),
                    "Voce nao possui permissao para acessar a imagem!",
                    "Permissão de Imagem",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        presenter.setImagemSelecionada(imagemSelecionada);
        presenter.setState(new ImagemViewSelecionadoState(presenter));
    }

    @Override
    public void excluir() {

    }

    @Override
    public void solicitarPermissao() {

        if (presenter.getUsuarioLogado().isAdmin()) {
            JOptionPane.showMessageDialog(
                    presenter.getTela(),
                    "O usuario Administrador nao precisa de permissoes!",
                    "Solicitar Permissoes",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String caminhoDaImagemSelecionada = presenter.getImagemSelecionada().getCaminho();
        Optional<Imagem> imagemSelecionadaOptional = presenter.getImagemRepository().findByCaminho(
                caminhoDaImagemSelecionada
        );

        if (imagemSelecionadaOptional.isEmpty()) {
            imagemSelecionadaOptional = presenter.getImagemRepository().save(
                    new Imagem(caminhoDaImagemSelecionada)
            );
        }

        List<Usuario> admins = presenter
                .getUsuarioRepository()
                .findAll()
                .stream()
                .filter(Usuario::isAdmin)
                .collect(Collectors.toList());

        Optional<Imagem> finalImagemSelecionadaOptional = imagemSelecionadaOptional;
        admins.forEach(admin -> finalImagemSelecionadaOptional.ifPresent(imagem -> {
            Notificacao notificacao = new Notificacao(
                    "Usuario " + presenter.getUsuarioLogado().getName() + " enviou solicitacao de Permissao da Imagem: "  + caminhoDaImagemSelecionada,
                    imagem,
                    admin
                    );
            presenter.getNotificacaoRepository().merge(notificacao);
        }));

        JOptionPane.showMessageDialog(
                presenter.getTela(),
                "Notificacao criada com sucesso!"
        );

    }

    @Override
    public void compartilhar() {

    }

    @Override
    public void fechar() {

    }
}
