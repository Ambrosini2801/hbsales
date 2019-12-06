package br.com.hbsis.produto;

import br.com.hbsis.categorialinha.CategoriaLinha;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    @Column(name = "cod_produto", unique = true, nullable = false, length = 10)
    private String codProduto;
    @CsvBindByPosition(position = 2)
    @Column(name = "nome_produto", unique = true, nullable = false, length = 200)
    private String nomeProduto;
    @CsvBindByPosition(position = 3)
    @Column(name = "preco_produto", unique = true, nullable = false, length = 25)
    private double precoProduto;
    @CsvBindByPosition(position = 4)
    @Column(name = "unidade_cx", unique = true, nullable = false, length = 25)
    private int unidadeCx;
    @CsvBindByPosition(position = 5)
    @Column(name = "peso_uni", unique = true, nullable = false, length = 25)
    private String pesoUni;
    @CsvBindByPosition(position = 6)
    @Column(name = "val_produto", unique = true, nullable = false)
    private LocalDate valProduto;
    @CsvBindByPosition(position = 7)
    @Column(name = "categoria_linha", unique = true, nullable = false)
    private CategoriaLinha categoriaLinha;

    public Produto(Long id, String codProduto, String nomeProduto, double precoProduto, int unidadeCx, String pesoUni, LocalDate valProduto, CategoriaLinha categoriaLinha) {
        this.id = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCx = unidadeCx;
        this.pesoUni = pesoUni;
        this.valProduto = valProduto;
        this.categoriaLinha = categoriaLinha;
    }

    public Produto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getUnidadeCx() {
        return unidadeCx;
    }

    public void setUnidadeCx(int unidadeCx) {
        this.unidadeCx = unidadeCx;
    }

    public String getPesoUni() {
        return pesoUni;
    }

    public void setPesoUni(String pesoUni) {
        this.pesoUni = pesoUni;
    }

    public LocalDate getValProduto() {
        return valProduto;
    }

    public void setValProduto(LocalDate valProduto) {
        this.valProduto = valProduto;
    }

    public CategoriaLinha getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(CategoriaLinha categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }
}