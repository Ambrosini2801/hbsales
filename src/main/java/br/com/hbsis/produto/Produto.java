package br.com.hbsis.produto;


import br.com.hbsis.categorialinha.CategoriaLinha;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    @Column(name = "cod_produto", unique = true, nullable = false, length = 100)
    private Long codProduto;
    @CsvBindByPosition(position = 2)
    @Column(name = "nome_produto", unique = true, nullable = false, length = 100)
    private String nomeProduto;
    @CsvBindByPosition(position = 3)
    @Column(name = "preco_produto", unique = true, nullable = false, length = 100)
    private Long precoProduto;
    @CsvBindByPosition(position = 4)
    @Column(name = "unidade_cx", unique = true, nullable = false, length = 100)
    private String unidadeCx;
    @CsvBindByPosition(position = 5)
    @Column(name = "peso_uni", unique = true, nullable = false, length = 100)
    private String pesoUni;
    @CsvBindByPosition(position = 6)
    @Column(name = "val_produto", unique = true, nullable = false, length = 100)
    private String valProduto;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    @CsvBindByPosition(position = 7)
    private CategoriaLinha categoriaLinha;

    public Produto() {

    }

    public Produto(Long id, Long codProduto,
                   String nomeProduto, Long precoProduto,
                   String unidadeCx, String pesoUni,
                   String valProduto, CategoriaLinha categoriaLinha) {
        this.id = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCx = unidadeCx;
        this.pesoUni = pesoUni;
        this.valProduto = valProduto;
        this.categoriaLinha = categoriaLinha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(Long codProduto) {
        this.codProduto = codProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Long getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(Long precoProduto) {
        this.precoProduto = precoProduto;
    }

    public String getUnidadeCx() {
        return unidadeCx;
    }

    public void setUnidadeCx(String unidadeCx) {
        this.unidadeCx = unidadeCx;
    }

    public String getPesoUni() {
        return pesoUni;
    }

    public void setPesoUni(String pesoUni) {
        this.pesoUni = pesoUni;
    }

    public String getValProduto() {
        return valProduto;
    }

    public void setValProduto(String valProduto) {
        this.valProduto = valProduto;
    }

    public CategoriaLinha getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(CategoriaLinha categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }
}
