package models;

import models.proxy.ImagemProxy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "imagens")
@NamedQuery(name = "Imagem.findByCaminho",
        query = "SELECT i FROM Imagem i WHERE i.caminho = :caminho")
public class Imagem {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String caminho;

    @Column
    private boolean excluida;

    @OneToMany(mappedBy = "imagem", cascade = { CascadeType.ALL })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PermissaoImagem> permissoesImagens = new ArrayList<>();

    @OneToMany(mappedBy = "imagem", cascade = { CascadeType.ALL })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Notificacao> notificacoes = new ArrayList<>();

    @Transient
    private ImagemProxy imagemProxy;

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public boolean isExcluida() {
        return excluida;
    }

    public void setExcluida(boolean excluida) {
        this.excluida = excluida;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Imagem(long id, String caminho, boolean excluida) {
        this.id = id;
        this.caminho = caminho;
        this.excluida = excluida;
    }

    public Imagem(String caminho, boolean excluida) {
        this.caminho = caminho;
        this.excluida = excluida;
    }

    public Imagem(String caminho) {
        this.caminho = caminho;
        this.excluida = false;
    }

    public Imagem() {

    }

    public ImagemProxy getImagemProxy() {
        return imagemProxy;
    }

    public void setImagemProxy(ImagemProxy imagemProxy) {
        this.imagemProxy = imagemProxy;
    }

    @Override
    public String toString() {
        return "Imagem{" +
                "id=" + id +
                ", caminho='" + caminho + '\'' +
                ", excluida=" + excluida +
                '}';
    }
}
