package br.com.hbsis.categorialinha;

public class CategoriaLinhaDTO {

    private Long id;
    private String codLinha;
    private String catLinha;
    private String nomeLinha;

    public CategoriaLinhaDTO() {
    }

    public CategoriaLinhaDTO(Long id, String codLinha, String catLinha, String nomeLinha) {
        this.id = id;
        this.codLinha = codLinha;
        this.catLinha = catLinha;
        this.nomeLinha = nomeLinha;
    }


     public static CategoriaLinhaDTO of(CategoriaLinha linhaExistente) {
        return new CategoriaLinhaDTO(
                linhaExistente.getId(),
                linhaExistente.getCodLinha(),
                linhaExistente.getCatLinha(),
                linhaExistente.getNomeLinha()

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

    @Override
    public String toString() {
        return "CategoriaLinhaDTO {codLinha=" + codLinha + ", " +
            "catLinha=" + catLinha + " ," +
                    "nomeLinha=" + nomeLinha + "}";

    }

}
