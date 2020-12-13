package business;

import models.Usuario;
import repository.UsuarioRepository;

import java.util.Optional;

public class Autenticacao {

    private UsuarioRepository usuarioRepository;

    public Autenticacao() {
        this.usuarioRepository = UsuarioRepository.getInstance();
    }

    public Usuario autenticar(String email, String password) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        throw new Error("E-mail ou senha estão incorretos.");
    }
}
