package presenter.states.imagens;

import presenter.ImagensViewPresenter;

public class ImagemViewInitialState extends ImagemViewAbstractState{
    public ImagemViewInitialState(ImagensViewPresenter presenter) {
        super(presenter);
        presenter.setBotoes(
                false,
                false,
                false,
                true
        );
    }


    @Override
    public void excluir() {
        throw new IllegalStateException("Não é possível excluir imagem nesse estado!");
    }

    @Override
    public void solicitarPermissao() {
        throw new IllegalStateException("Não é possível solicitar permissao nesse estado!");
    }

    @Override
    public void compartilhar()  {
        throw new IllegalStateException("Não é possível compartilhar imagem nesse estado!");
    }

}
