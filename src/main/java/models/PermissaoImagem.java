package models;

import javax.persistence.*;

@Entity
@Table(name = "permissoes_imagens")
public class PermissaoImagem {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idImagem")
    private Imagem imagem;

    @Column
    private boolean escrita;

    @Column
    private boolean exclusao;

    @Column
    private boolean compartilhamento;

    public PermissaoImagem() {
    }

    public PermissaoImagem(long id, Usuario usuario, Imagem imagem, boolean escrita, boolean exclusao, boolean compartilhamento) {
        this.id = id;
        this.usuario = usuario;
        this.imagem = imagem;
        this.escrita = escrita;
        this.exclusao = exclusao;
        this.compartilhamento = compartilhamento;
    }

    public PermissaoImagem(Usuario usuario, Imagem imagem, boolean escrita, boolean exclusao, boolean compartilhamento) {
        this.usuario = usuario;
        this.imagem = imagem;
        this.escrita = escrita;
        this.exclusao = exclusao;
        this.compartilhamento = compartilhamento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public boolean isEscrita() {
        return escrita;
    }

    public void setEscrita(boolean escrita) {
        this.escrita = escrita;
    }

    public boolean isExclusao() {
        return exclusao;
    }

    public void setExclusao(boolean exclusao) {
        this.exclusao = exclusao;
    }

    public boolean isCompartilhamento() {
        return compartilhamento;
    }

    public void setCompartilhamento(boolean compartilhamento) {
        this.compartilhamento = compartilhamento;
    }
}
