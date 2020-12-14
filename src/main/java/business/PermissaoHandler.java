package business;

import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;
import repository.PermissoesImagemRepository;

import java.util.Optional;

public class PermissaoHandler {

    private final PermissoesImagemRepository permissaoImagemRepository;

    public PermissaoHandler(boolean testDatabase) {
        this.permissaoImagemRepository = PermissoesImagemRepository.getInstance(testDatabase);
    }

    public void definirPermissoes(Usuario usuario, Imagem imagem, boolean visualizacao, boolean exclusao, boolean compartilhamento) {
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario, imagem);
        if (permissaoImagemOptional.isPresent()) {
            PermissaoImagem permissaoImagem = permissaoImagemOptional.get();
            permissaoImagem.setVisualizacao(visualizacao);
            permissaoImagem.setExclusao(exclusao);
            permissaoImagem.setCompartilhamento(compartilhamento);
            this.permissaoImagemRepository.update(permissaoImagem);

        }
        PermissaoImagem permissaoImagem = new PermissaoImagem(usuario, imagem, visualizacao, exclusao, compartilhamento);
        this.permissaoImagemRepository.save(permissaoImagem);
    }

    public boolean temPermissaoParaVisualizar(Usuario usuario, Imagem imagem) {
        if (usuario.getCargo().getCode().equals("admin")) {
            return true;
        }
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario, imagem);
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isVisualizacao();
    }

    public boolean temPermissaoParaCompartilhar(Usuario usuario, Imagem imagem) {
        if (usuario.getCargo().getCode().equals("admin")) {
            return true;
        }
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario, imagem);
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isCompartilhamento();
    }

    public boolean temPermissaoParaExcluir(Usuario usuario, Imagem imagem) {
        if (usuario.getCargo().getCode().equals("admin")) {
            return true;
        }
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario, imagem);
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isExclusao();
    }
}
