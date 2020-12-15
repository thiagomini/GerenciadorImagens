package presenter.states.imagens;

import business.PermissaoHandler;
import models.Imagem;
import models.proxy.ImagemProxy;
import presenter.ImagensViewPresenter;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

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



    }

    @Override
    public void excluir() {

    }

    @Override
    public void solicitarPermissao() {

    }

    @Override
    public void compartilhar() {

    }

    @Override
    public void fechar() {

    }
}
