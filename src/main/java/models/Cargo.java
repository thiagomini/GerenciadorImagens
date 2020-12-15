package models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "cargos" )
@NamedQuery(name = "Cargo.findByCode",
        query = "SELECT c FROM Cargo c WHERE c.code = :code")
public class Cargo {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String nome;

    @Column
    private String code;

    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Usuario> usuarios = new ArrayList<>();

    public Cargo(long id, String nome, String code) {
        this.id = id;
        this.nome = nome;
        this.code = code;
    }

    public Cargo(String nome, String code) {
        this.nome = nome;
        this.code = code;
    }

    public Cargo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
