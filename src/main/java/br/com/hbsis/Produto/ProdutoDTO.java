package br.com.hbsis.Produto;

import java.time.LocalDate;

public class ProdutoDTO {

    private Long id;
    private String codProduto;
    private String nomeProduto;
    private double precoProduto;
    private int unidadeCx;
    private double pesoUni;
    private String unidadePeso;
    private LocalDate valProduto;
    private Long categoriaLinha;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, Long categoriaLinha, String codProduto, String nomeProduto, double precoProduto, int unidadeCx, double pesoUni, String unidadePeso, LocalDate valProduto) {
        this.id = id;
        this.categoriaLinha = categoriaLinha;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCx = unidadeCx;
        this.pesoUni = pesoUni;
        this.unidadePeso = unidadePeso;
        this.valProduto = valProduto;

    }

    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getCategoriaLinha().getId(),
                produto.getCodProduto(),
                produto.getNomeProduto(),
                produto.getPrecoProduto(),
                produto.getUnidadeCx(),
                produto.getPesoUni(),
                produto.getUnidadePeso(),
                produto.getValProduto()
        );
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

    public Long getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(Long categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }


    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "id=" + id +
                ", categoriaLinhaId=" + categoriaLinha +
                ", codProduto='" + codProduto + '\'' +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", precoProduto=" + precoProduto +
                ", unidadeCx=" + unidadeCx +
                ", pesoUni=" + pesoUni +
                ", unidadePeso='" + unidadePeso + '\'' +
                ", valProduto=" + valProduto +
                '}';
    }
}