package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;


import javax.persistence.*;


@Entity
@Table(name = "linha")
public class CategoriaLinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_linha", unique = true, nullable = false, length = 10)
    private String codLinha;
    @Column(name = "nome_linha", unique = true, nullable = false, length = 50)
    private String nomeLinha;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

    public CategoriaLinha() {
    }

    public CategoriaLinha(Long id, String codLinha, String nomeLinha, Categoria categoria) {
        this.id = id;
        this.codLinha = codLinha;
        this.nomeLinha = nomeLinha;
        this.categoria = categoria;

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
        return "CategoriaLinha{" +
                "id=" + id +
                ", codLinha='" + codLinha + '\'' +
                ", nomeLinha='" + nomeLinha + '\'' +
                ", categoria=" + categoria +
                '}';

    }

}