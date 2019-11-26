package br.com.hbsis.categoria;

import javax.persistence.*;

@Entity
@Table(name = "br/com/hbsis/categoria")
public class Categoria {

    public Object Categoria;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sku", unique = true, length = 8)
    private String sku;
    @Column(name = "fornecedorCategoria", unique = true, length = 100)
    private String fornecedorCategoria;
    @Column(name = "nomeCategoria", nullable = true, updatable = true, length = 100)
    private String nomeCategoria;

    public Long getId() {
        return id;
    }

    public String getsku() {
        return sku;
    }

    public void setsku(String sku) {
        this.sku = sku;
    }

    public String getFornecedorCategoria() {
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

    @Override
    public String toString() {
        return "categoria [SKU=" + sku + ", fornecedorCategoria=" + fornecedorCategoria +
                " + nomeCategoria=" + nomeCategoria + "]";


    }


}



