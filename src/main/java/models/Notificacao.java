package models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table( name="notificacoes" )
@NamedQuery(name = "Notificacao.findByUser",
        query = "SELECT n FROM Notificacao n JOIN n.usuario u WHERE u.id=:usuarioId")
public class Notificacao {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idImagem")
    private Imagem imagem;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column
    private boolean lida;

    public Notificacao() {

    }

    public Notificacao(long id, String descricao, Imagem imagem, Usuario usuario, boolean lida) {
        this.id = id;
        this.descricao = descricao;
        this.imagem = imagem;
        this.usuario = usuario;
        this.lida = lida;
    }

    public Notificacao(String descricao, Imagem imagem, Usuario usuario) {
        this.descricao = descricao;
        this.imagem = imagem;
        this.usuario = usuario;
        this.lida = false;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
            return getDescricao();
    }
}
