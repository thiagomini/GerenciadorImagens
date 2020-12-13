package repository;

import models.Cargo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CargoRepositoryTest {

    CargoRepository repository;

    @BeforeAll
    void setUp() {
        this.repository = CargoRepository.getInstance();
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
        this.repository.clearEntityManager();
    }


    /**
     * Função <b>save(cargo)</b>
     * Deve salvar um novo cargo corretamente
     */
    @Test
    void CT012() {
        Cargo cargo = new Cargo("Administrador", "admin");
        Optional<Cargo> cargoOptional = this.repository.save(cargo);

        assertTrue(cargoOptional.isPresent());
        assertTrue(cargo.getId() > 0, "Id do cargo " + cargo.getId() + " deve existir.");
    }

    /**
     * Função <b>findById(id)</b>
     * Deve retornar corretamente um cargo pelo Id
     */
    @Test
    void CT013() {
        Cargo cargo = new Cargo("Administrador", "admin");
        this.repository.save(cargo);

        Optional<Cargo> cargoEncontrado = this.repository.findById(cargo.getId());
        assertTrue(cargoEncontrado.isPresent());
    }

    /**
     * Função <b>findAll()</b>
     * Deve retornar corretamente todos os cargos
     */
    @Test
    void CT014() {
        Cargo cargo = new Cargo("Administrador", "admin");
        Cargo cargo2 = new Cargo("Usuario", "user");
        this.repository.save(cargo);
        this.repository.save(cargo2);

        ArrayList<Cargo> listaCargos = (ArrayList<Cargo>) this.repository.findAll();

        assertEquals(2, listaCargos.size());
    }

    /**
     * Função <b>delete(cargo)</b>
     * Deve deletar corretamente um cargo pela referência
     */
    @Test
    void CT015() {
        Cargo cargo = new Cargo("Administrador", "admin");
        this.repository.save(cargo);

        this.repository.delete(cargo);

        Optional<Cargo> cargoEncontrado = this.repository.findById(cargo.getId());

        assertTrue(cargoEncontrado.isEmpty());
    }

    /**
     * Função <b>deleteById(id)</b>
     * Deve deletar corretamente um cargo pelo id
     */
    @Test
    void CT016() {
        Cargo cargo = new Cargo("Administrador", "admin");
        this.repository.save(cargo);

        this.repository.deleteById(cargo.getId());

        Optional<Cargo> cargoEncontrado = this.repository.findById(cargo.getId());

        assertTrue(cargoEncontrado.isEmpty());
    }





}