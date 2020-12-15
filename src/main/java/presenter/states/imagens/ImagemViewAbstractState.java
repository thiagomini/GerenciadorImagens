package presenter.states.imagens;

import presenter.ImagensViewPresenter;

public class ImagemViewAbstractState implements ImagemViewState{

    private ImagensViewPresenter presenter;

    public ImagemViewAbstractState(ImagensViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void selecionarItemLista() {

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
