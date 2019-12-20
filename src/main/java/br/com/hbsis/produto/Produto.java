package br.com.hbsis.produto;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categorialinha.CategoriaLinha;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_produto", unique = true, nullable = false, length = 10)
    private String codProduto;
    @Column(name = "nome_produto", unique = true, nullable = false, length = 200)
    private String nomeProduto;
    @Column(name = "preco_produto", unique = true, nullable = false, length = 25)
    private double precoProduto;
    @Column(name = "unidade_cx", unique = true, nullable = false, length = 25)
    private int unidadeCx;
    @Column(name = "peso_uni", unique = true, nullable = false, length = 25)
    private String pesoUni;
    @Column(name = "val_produto", nullable = false, length = 8)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate valProduto;

    @ManyToOne
    @JoinColumn(name = "categoria_linha", referencedColumnName = "id")
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

    public Categoria getCategoria() {return categoria;}


    }
}