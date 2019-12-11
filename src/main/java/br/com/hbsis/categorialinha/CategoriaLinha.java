package br.com.hbsis.categorialinha;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.produto.Produto;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "linha")
public class CategoriaLinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    @Column(name = "cod_linha", unique = true, nullable = false, length = 100)
    private String codLinha;
    @CsvBindByPosition(position = 2)
    @Column(name = "cat_linha", unique = true, nullable = false, length = 100)
    private String catLinha;
    @CsvBindByPosition(position = 3)
    @Column(name = "nome_linha", unique = true, nullable = false, length = 100)
    private String nomeLinha;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    @CsvBindByPosition(position = 4)
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "categoriaLinha", cascade = CascadeType.ALL)

    List<Produto> produto;

    public List<Produto> getProduto() {
        return produto;
    }
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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "CategoriaLinha{codLinha=" + codLinha + ", " +
                "catLinha=" + catLinha + " ," +
                "nomeLinha=" + nomeLinha + "}";

    }

}