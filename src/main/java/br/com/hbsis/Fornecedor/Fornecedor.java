package br.com.hbsis.Fornecedor;

import br.com.hbsis.categoria.Categoria;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cad_fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_social", unique = true, nullable = false, length = 100)
    private String razaoSocial;
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;
    @Column(name = "nome_fantasia", nullable = false, updatable = true, length = 100)
    private String nome_fantasia;
    @Column(name = "endereco", nullable = false, updatable = true, length = 100)
    private String endereco;
    @Column(name = "telefone", nullable = false, updatable = true, length = 50)
    private String telefone;
    @Column(name = "email", nullable = false, updatable = false, length = 100)
    private String email;
    @OneToMany(mappedBy = "fornecedor", targetEntity = Categoria.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Categoria> categoria;

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categoria = categorias;
    }


    public Fornecedor(Object id, Object categoria) {
    }

    public Fornecedor() {

    }

    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCNPJ() {
        return cnpj;
    }

    public void setCNPJ(String CNPJ) {
        this.cnpj = CNPJ;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fornecedor(Long id, String razaoSocial, String CNPJ, String nome_fantasia, String endereco, String telefone, String email) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.cnpj = CNPJ;
        this.nome_fantasia = nome_fantasia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "categoria [razao_social = " + razaoSocial + ", cnpj =" + cnpj + " ," +
                " nome_fantasia = " + nome_fantasia + ", endereco = " + endereco + "telefone = " + telefone + "]";


    }


}
