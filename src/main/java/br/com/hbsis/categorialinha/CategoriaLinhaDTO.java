package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;

public class CategoriaLinhaDTO {

    private Long id;
    private String codLinha;
    private String nomeLinha;
    private String catLinha;
    private Categoria categoria;

    public CategoriaLinhaDTO() {
    }

    public CategoriaLinhaDTO(Long id, String codLinha, String nomeLinha, String catLinha, Categoria categoria) {
        this.id = id;
        this.codLinha = codLinha;
        this.nomeLinha = nomeLinha;
        this.catLinha = catLinha;
    }

    public static CategoriaLinhaDTO of(CategoriaLinha linhaexistente) {
        return new CategoriaLinhaDTO(
                linhaexistente.getId(),
                linhaexistente.getCodLinha(),
                linhaexistente.getNomeLinha(),
                linhaexistente.getCatLinha(),
                linhaexistente.getCategoria()

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

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public String getCatLinha() {
        return catLinha;
    }

    public void setCatLinha(String catLinha) {
        this.catLinha = catLinha;
    }

    public Categoria getCategoria(Categoria categoria) {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;

    }

    @Override
    public String toString() {
        return "categoria [codLinha=" + codLinha + ", nomeLinha=" + nomeLinha + " ," +
                " catLinha=" + catLinha + "]";

    }

}
