package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;

@Entity
@Table(name = "categorialinha")
public class CategoriaLinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    @Column(name = "cod_linha", unique = true, nullable = false, length = 100)
    private String codLinha;
    @CsvBindByPosition(position = 2)
    @Column(name = "nome_linha")
    private String nomeLinha;
    @CsvBindByPosition(position = 3)
    @Column(name = "cat_linha")
    private String catLinha;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @CsvBindByPosition(position = 3)
    private Categoria categoria;

    public CategoriaLinha() {

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

    public Categoria getCategoria() {
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