import models.Cargo;
import models.Imagem;
import models.Usuario;
import repository.CargoRepository;
import repository.ImagemRepository;
import repository.UsuarioRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class main {
    public static void main(String[] args) {
        // Create our entity manager
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GerenciadorDeImagens");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        CargoRepository cargoRepository = new CargoRepository(entityManager);
        UsuarioRepository usuarioRepository = new UsuarioRepository(entityManager);
        ImagemRepository imagemRepository = new ImagemRepository(entityManager);

        Cargo admin = new Cargo("Administrador", "admin");
        Cargo userNormal = new Cargo("Usuário Normal", "user");

        Optional<Cargo> cargoAdmin = cargoRepository.save(admin);
        Optional<Cargo> cargoUser = cargoRepository.save(userNormal);

        cargoAdmin.ifPresent(value -> System.out.println("Novo Cargo Salvo: " + value));
        cargoUser.ifPresent(value -> System.out.println("Novo Cargo Salvo: " + value));

        List<Cargo> cargoList = cargoRepository.findAll();
        cargoList.forEach(System.out::println);
        entityManager.close();
        entityManagerFactory.close();
    }
}
