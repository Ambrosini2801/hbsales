package br.com.hbsis.Categoria;

import br.com.hbsis.Fornecedor.Fornecedor;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @Column(name = "id_categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_categoria", unique = true, nullable = false, length = 50)
    private String nomeCategoria;
    @Column(name = "cod_categoria", unique = true, nullable = false, length = 10)
    private String codCategoria;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;

    public Categoria() {
    }

    public Categoria(Long id, String nomeCategoria, String codCategoria, Fornecedor fornecedor) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.codCategoria = codCategoria;
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", codCategoria='" + codCategoria + '\'' +
                ", fornecedor=" + fornecedor +
                '}';
    }
}