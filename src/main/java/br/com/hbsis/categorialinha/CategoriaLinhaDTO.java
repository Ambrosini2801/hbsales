package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;

public class CategoriaLinhaDTO {

    private Long id;
    private String codLinha;
    private String nomeLinha;
    private Categoria categoria;

    public CategoriaLinhaDTO(Long id, String codLinha, String nomeLinha, Categoria categoria) {
    }

    public static CategoriaLinhaDTO of(CategoriaLinha linhaExistente) {
        CategoriaLinhaDTO categoriaLinhaDTO = null;
        return new CategoriaLinhaDTO(
                linhaExistente.getId(),
                linhaExistente.getCodLinha(),
                linhaExistente.getNomeLinha(),
                linhaExistente.getCategoria(categoriaLinhaDTO)
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "CategoriaLinhaDTO{" +
                "id=" + id +
                ", codLinha='" + codLinha + '\'' +
                ", nomeLinha='" + nomeLinha + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}