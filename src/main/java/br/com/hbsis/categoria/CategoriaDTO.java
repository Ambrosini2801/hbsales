package br.com.hbsis.categoria;

public class CategoriaDTO {

    private Long id;
    private String sku;
    private String fornecedorCategoria;
    private String nomeCategoria;

    public CategoriaDTO(){

    }

    public CategoriaDTO(Long id, String sku) {
        this.id = id;
        this.sku = sku;
    }

    public CategoriaDTO(Long id, String sku, String fornecedorCategoria, String nomeCategoria) {
        this.id = id;
        this.sku = sku;
        this.fornecedorCategoria = fornecedorCategoria;
        this.nomeCategoria = nomeCategoria;
    }

    public static CategoriaDTO of(Categoria categoriaDTO) {
        return new CategoriaDTO(categoriaDTO.getId(),
                categoriaDTO.getFornecedorCategoria(),
                categoriaDTO.getsku(),
                categoriaDTO.getNomeCategoria());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getsku() {
        return sku;
    }

    public void setsku(String sku) {
        this.sku = sku;
    }

    public  String getFornecedorCategoria() {
        return fornecedorCategoria;
    }

    public void setFornecedorCategoria(String fornecedorCategoria) {
        this.fornecedorCategoria = fornecedorCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

}






