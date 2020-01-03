package br.com.hbsis.categorialinha;

public class CategoriaLinhaDTO {

    private Long id;
    private String codLinha;
    private String nomeLinha;
    private Long id_categoria;

    public CategoriaLinhaDTO() {
    }


    public CategoriaLinhaDTO(Long id, String codLinha, String nomeLinha, Long id_categoria) {
        this.id = id;
        this.codLinha = codLinha;
        this.nomeLinha = nomeLinha;
        this.id_categoria = id_categoria;
    }

    public CategoriaLinhaDTO(Long id, Long codLinha, Long nomeLinha, Long id1) {
    }

    public static CategoriaLinhaDTO of(CategoriaLinha categoriaLinha) {
        return new CategoriaLinhaDTO(

        categoriaLinha.getId(),
        categoriaLinha.getCodLinha(),
        categoriaLinha.getNomeLinha(),
        categoriaLinha.getCategoria().getId());

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

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    @Override
    public String toString() {
        return "CategoriaLinhaDTO{" +
                "id=" + id +
                ", codLinha='" + codLinha + '\'' +
                ", nomeLinha='" + nomeLinha + '\'' +
                ", id_categoria=" + id_categoria +
                '}';
    }
}