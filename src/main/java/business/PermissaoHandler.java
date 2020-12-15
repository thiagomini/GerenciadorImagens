package business;

import models.Imagem;
import models.PermissaoImagem;
import models.Usuario;
import repository.ImagemRepository;
import repository.PermissoesImagemRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class PermissaoHandler {

    private final PermissoesImagemRepository permissaoImagemRepository;
    private final ImagemRepository imagemRepository;

    public PermissaoHandler(boolean testDatabase) {
        this.permissaoImagemRepository = PermissoesImagemRepository.getInstance(testDatabase);
        imagemRepository = ImagemRepository.getInstance(testDatabase);
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
        if (usuario.isAdmin()) {
            return true;
        }
        Optional<Imagem> imagemAtualizada = imagemRepository.merge(imagem);

        if (imagemAtualizada.isEmpty()) return false;

        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario.getId(),  imagemAtualizada.get().getId());
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isVisualizacao();
    }

    public boolean temPermissaoParaCompartilhar(Usuario usuario, Imagem imagem) {
        if (usuario.isAdmin()) {
            return true;
        }
        Optional<Imagem> imagemAtualizada = imagemRepository.merge(imagem);
        if (imagemAtualizada.isEmpty()) return false;

        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario.getId(),  imagemAtualizada.get().getId());

        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isCompartilhamento();
    }

    public boolean temPermissaoParaExcluir(Usuario usuario, Imagem imagem) {
        if (usuario.isAdmin()) {
            return true;
        }
        Optional<Imagem> imagemAtualizada = imagemRepository.merge(imagem);

        if (imagemAtualizada.isEmpty()) return false;

        Optional<PermissaoImagem> permissaoImagemOptional = this.permissaoImagemRepository.findByUserAndImage(usuario.getId(),  imagemAtualizada.get().getId());
        if (permissaoImagemOptional.isEmpty()) {
            return false;
        }
        return permissaoImagemOptional.get().isExclusao();
    }

}
