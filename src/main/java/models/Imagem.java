package models;

import javax.persistence.*;

@Entity
@Table(name = "imagens")
@NamedQuery(name = "Imagem.findByCaminho",
        query = "SELECT i FROM Imagem i WHERE i.caminho = :caminho")
public class Imagem {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String caminho;

    @Column
    private boolean excluida;

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

    @Override
    public String toString() {
        return "Imagem{" +
                "id=" + id +
                ", caminho='" + caminho + '\'' +
                ", excluida=" + excluida +
                '}';
    }
}
