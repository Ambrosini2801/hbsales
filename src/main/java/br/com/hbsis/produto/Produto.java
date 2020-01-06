package br.com.hbsis.produto;

import br.com.hbsis.categorialinha.CategoriaLinha;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_produto", unique = true, nullable = false, length = 10)
    private String codProduto;
    @Column(name = "nome_produto", unique = true, nullable = false, length = 200)
    private String nomeProduto;
    @Column(name = "preco", unique = true, nullable = false, length = 9)
    private double precoProduto;
    @Column(name = "unidade_cx", unique = true, nullable = false, length = 25)
    private int unidadeCx;
    @Column(name = "peso_uni", unique = true, nullable = false, length = 9)
    private double pesoUni;
    @Column(name = "unidade_peso", nullable = false, length = 5)
    private String unidadePeso;
    @Column(name = "val_produto", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate valProduto;

    @ManyToOne
    @JoinColumn(name = "fk_id_linha", referencedColumnName = "id")
    private CategoriaLinha categoriaLinha;

    public Produto() {

    }

    public Produto(Long id, String codProduto, String nomeProduto, double precoProduto, int unidadeCx, double pesoUni, String unidadePeso, LocalDate valProduto, CategoriaLinha categoriaLinha) {
        this.id = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCx = unidadeCx;
        this.pesoUni = pesoUni;
        this.unidadePeso = unidadePeso;
        this.valProduto = valProduto;
        this.categoriaLinha = categoriaLinha;
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

    public double getPesoUni() {
        return pesoUni;
    }

    public void setPesoUni(double pesoUni) {
        this.pesoUni = pesoUni;
    }

    public String getUnidadePeso() {
        return unidadePeso;
    }

    public void setUnidadePeso(String unidadePeso) {
        this.unidadePeso = unidadePeso;
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

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", codProduto='" + codProduto + '\'' +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", precoProduto=" + precoProduto +
                ", unidadeCx=" + unidadeCx +
                ", pesoUni=" + pesoUni +
                ", unidadePeso='" + unidadePeso + '\'' +
                ", valProduto=" + valProduto +
                ", categoriaLinha=" + categoriaLinha +
                '}';
    }
}