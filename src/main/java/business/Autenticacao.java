package business;

import models.Usuario;
import repository.UsuarioRepository;

import java.util.Optional;

public class Autenticacao {

    private UsuarioRepository usuarioRepository;

    public Autenticacao(boolean testDatabase) {
        this.usuarioRepository = UsuarioRepository.getInstance(testDatabase);
    }

    public Usuario autenticar(String email, String password) throws RuntimeException, IllegalAccessException {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        throw new IllegalAccessException("E-mail ou senha estão incorretos.");
    }
}
