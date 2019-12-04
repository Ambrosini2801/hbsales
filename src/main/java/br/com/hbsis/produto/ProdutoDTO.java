package br.com.hbsis.produto;

public class ProdutoDTO {

    private Long id;
    private Long codProduto;
    private String nomeProduto;
    private Long precoProduto;
    private String unidadeCx;
    private String pesoUni;
    private String valProduto;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, Long codProduto,
                      String nomeProduto, Long precoProduto,
                      String unidadeCx, String pesoUni,
                      String valProduto) {
        this.id = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCx = unidadeCx;
        this.pesoUni = pesoUni;
        this.valProduto = valProduto;
    }

    public static ProdutoDTO of(Produto produtoexistente) {
        return new ProdutoDTO(
                produtoexistente.getId(),
                produtoexistente.getCodProduto(),
                produtoexistente.getNomeProduto(),
                produtoexistente.getPrecoProduto(),
                produtoexistente.getUnidadeCx(),
                produtoexistente.getPesoUni(),
                produtoexistente.getValProduto()
        );
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
}
