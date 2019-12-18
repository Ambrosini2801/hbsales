package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.produto.Produto;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "linha")
public class CategoriaLinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_linha", unique = true, nullable = false, length = 10)
    private String codLinha;
    @Column(name = "cat_linha", unique = true, nullable = false, length = 100)
    private String catLinha;
    @Column(name = "nome_linha", unique = true, nullable = false, length = 50)
    private String nomeLinha;
    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    private Categoria categoria;

    @OneToMany(mappedBy = "categoriaLinha", cascade = CascadeType.ALL)

    List<Produto> produto;

    public void setProduto(List<Produto> produtos) {
        this.produto = produtos;
    }

    public CategoriaLinha() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodLinha() {
        return codLinha;
    }

    public void setCodLinha(String codLinha) {
        this.codLinha = codLinha;
    }

    public String getCatLinha() {
        return catLinha;
    }

    public void setCatLinha(String catLinha) {
        this.catLinha = catLinha;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CategoriaLinha(Long id, String nomeLinha, String codLinha, String catLinha, Categoria categoria) {
        this.id = id;
        this.nomeLinha = nomeLinha;
        this.codLinha = codLinha;
        this.catLinha = catLinha;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "CategoriaLinha{codLinha=" + codLinha + ", " +
                "catLinha=" + catLinha + " ," +
                "nomeLinha=" + nomeLinha + "}";

    }

}