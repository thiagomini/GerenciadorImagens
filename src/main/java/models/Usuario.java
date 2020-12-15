package models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="idCargo")
    private Cargo cargo;

    @OneToMany(mappedBy = "usuario", cascade = { CascadeType.ALL })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PermissaoImagem> permissoesImagens = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = { CascadeType.ALL })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Notificacao> notificacoes = new ArrayList<>();

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

    public Usuario(long id, String name, String email, String password, Cargo cargo, List<PermissaoImagem> permissoesImagens) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cargo = cargo;
        this.permissoesImagens = permissoesImagens;
    }

    public Usuario(String name, String email, String password, Cargo cargo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cargo = cargo;
    }

    public Usuario(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<PermissaoImagem> getPermissoesImagens() {
        return permissoesImagens;
    }

    public void setPermissoesImagens(List<PermissaoImagem> permissoesImagens) {
        this.permissoesImagens = permissoesImagens;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }
}

