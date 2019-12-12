package br.com.hbsis.produto;

import java.time.LocalDate;

public class ProdutoDTO {

    private Long id;
    private String codProduto;
    private String nomeProduto;
    private double precoProduto;
    private int unidadeCx;
    private String pesoUni;
    private LocalDate valProduto;
    private long categoriaLinha;

    public ProdutoDTO() {

    }

    public ProdutoDTO(Long id, String codProduto, String nomeProduto, double precoProduto, int unidadeCx, String pesoUni, LocalDate valProduto, long categoriaLinha) {
        this.id = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCx = unidadeCx;
        this.pesoUni = pesoUni;
        this.valProduto = valProduto;
        this.categoriaLinha = categoriaLinha;
    }

    public static ProdutoDTO of(Produto produtoexistente) {
        return new ProdutoDTO(
                produtoexistente.getId(),
                produtoexistente.getCodProduto(),
                produtoexistente.getNomeProduto(),
                produtoexistente.getPrecoProduto(),
                produtoexistente.getUnidadeCx(),
                produtoexistente.getPesoUni(),
                produtoexistente.getValProduto(),
                produtoexistente.getCategoriaLinha().getId()
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

    public long getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(long categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }
}
