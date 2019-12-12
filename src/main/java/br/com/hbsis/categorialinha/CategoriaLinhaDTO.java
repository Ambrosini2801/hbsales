package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;

public class CategoriaLinhaDTO {

    private Long id;
    private String codLinha;
    private String catLinha;
    private String nomeLinha;
    private Categoria categoria;

    public CategoriaLinhaDTO() {
    }

    public CategoriaLinhaDTO(Long id, String codLinha, String catLinha, String nomeLinha, Categoria categoria) {
        this.id = id;
        this.codLinha = codLinha;
        this.catLinha = catLinha;
        this.nomeLinha = nomeLinha;
        this.categoria = categoria;
    }

    public static CategoriaLinhaDTO of(CategoriaLinha linhaExistente) {
        return new CategoriaLinhaDTO(
                linhaExistente.getId(),
                linhaExistente.getCodLinha(),
                linhaExistente.getCatLinha(),
                linhaExistente.getNomeLinha(),
                linhaExistente.getCategoria()
        );
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

    @Override
    public String toString() {
        return "CategoriaLinhaDTO {codLinha=" + codLinha + ", " +
                "catLinha=" + catLinha + " ," +
                "nomeLinha=" + nomeLinha + "}";

    }

}