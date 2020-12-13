package business;

import models.Usuario;
import repository.UsuarioRepository;

import javax.persistence.NoResultException;
import java.util.Optional;

public class Autenticacao {

    private UsuarioRepository usuarioRepository;

    public Autenticacao() {
        this.usuarioRepository = UsuarioRepository.getInstance();
    }

    public Usuario autenticar(String email, String password) throws RuntimeException {
        RuntimeException exception = new RuntimeException("E-mail ou senha estão incorretos.");
        Optional<Usuario> usuarioOptional;

        try {
            usuarioOptional = this.usuarioRepository.findByEmail(email);
        } catch (NoResultException ex) {
            throw exception;
        }

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }

        throw exception;
    }
}
