package presenter.states.imagens;

import presenter.ImagensViewPresenter;

public class ImagemViewSelecionadoState extends ImagemViewAbstractState{

    public ImagemViewSelecionadoState(ImagensViewPresenter presenter) {
        super(presenter);
        presenter.setBotoes(true, true, true, true);
    }
}
