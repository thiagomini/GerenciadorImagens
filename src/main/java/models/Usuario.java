package models;

import javax.persistence.*;

@Entity
@Table( name = "usuarios" )
public class Usuario {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name="idCargo")
    private Cargo cargo;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cargo=" + cargo +
                '}';
    }

    public Usuario() {
    }

    public Usuario(long id, String name, String email, String password, Cargo cargo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cargo = cargo;
    }

    public Usuario(String name, String email, String password, Cargo cargo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cargo = cargo;
    }
}

