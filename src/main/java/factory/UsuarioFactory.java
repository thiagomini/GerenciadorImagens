package factory;

import models.Cargo;
import models.Usuario;
import repository.CargoRepository;
import repository.UsuarioRepository;

import java.util.Optional;

public class UsuarioFactory {

    UsuarioRepository usuarioRepository;
    CargoRepository cargoRepository;

    public UsuarioFactory(boolean testDatabase) {
        usuarioRepository = UsuarioRepository.getInstance(testDatabase);
        cargoRepository = CargoRepository.getInstance(testDatabase);
    }

    public Usuario createUsuario(String nome, String email, String senha) {
        Cargo cargo = getCargo();
        Usuario usuario = new Usuario(nome, email, senha, cargo);
        usuarioRepository.save(usuario);
        return usuario;
    }

    private Cargo getCargo() {
        if (usuarioRepository.hasOneRegistered()) {
            Optional<Cargo> cargoOptional = cargoRepository.findByCode("common_user");
            return cargoOptional.orElse(new Cargo("Usuario Comum", "common_user"));

        }
        else {
            Optional<Cargo> cargoOptional = cargoRepository.findByCode("admin");
            return cargoOptional.orElse(new Cargo("Usuario Administrador", "admin"));
        }
    }

}
