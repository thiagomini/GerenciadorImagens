package business;

import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;
import repository.PermissoesImagemRepository;

import java.util.Optional;

public class PermissaoHandler {

    private final PermissoesImagemRepository permissaoImagemRepository;
    private final String ADMIN_CODE = "admin";

    public PermissaoHandler(boolean testDatabase) {
        this.permissaoImagemRepository = PermissoesImagemRepository.getInstance(testDatabase);
    }

    public void definirPermissoes(Usuario usuario, Imagem imagem, boolean visualizacao, boolean exclusao, boolean compartilhamento) {
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario.getId(), imagem.getId());
        if (permissaoImagemOptional.isPresent()) {
            PermissaoImagem permissaoImagem = permissaoImagemOptional.get();
            permissaoImagem.setVisualizacao(visualizacao);
            permissaoImagem.setExclusao(exclusao);
            permissaoImagem.setCompartilhamento(compartilhamento);
            this.permissaoImagemRepository.update(permissaoImagem);
        }
        else {
            PermissaoImagem permissaoImagem = new PermissaoImagem(usuario, imagem, visualizacao, exclusao, compartilhamento);
            this.permissaoImagemRepository.save(permissaoImagem);
        }
    }

    public boolean temPermissaoParaVisualizar(Usuario usuario, Imagem imagem) {
        if (usuario.getCargo().getCode().equals(ADMIN_CODE)) {
            return true;
        }
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario.getId(), imagem.getId());
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isVisualizacao();
    }

    public boolean temPermissaoParaCompartilhar(Usuario usuario, Imagem imagem) {
        if (usuario.getCargo().getCode().equals(ADMIN_CODE)) {
            return true;
        }
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario.getId(), imagem.getId());
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isCompartilhamento();
    }

    public boolean temPermissaoParaExcluir(Usuario usuario, Imagem imagem) {
        if (usuario.getCargo().getCode().equals(ADMIN_CODE)) {
            return true;
        }
        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario.getId(), imagem.getId());
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isExclusao();
    }
}
