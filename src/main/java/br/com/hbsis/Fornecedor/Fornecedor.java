package br.com.hbsis.Fornecedor;
import javax.persistence.*;

@Entity
@Table(name = "cad_fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_social", unique = true, length = 100)
    private String razao_social;
    @Column(name = "cnpj", unique = true, length = 14)
    private String CNPJ;
    @Column(name = "nome_fantasia", nullable = true, updatable = true, length = 100)
    private String nome_fantasia;
    @Column(name = "endereco", nullable = true, updatable = true, length = 100)
    private String endereco;
    @Column(name = "telefone", nullable = true, updatable = true, length = 50)
    private String telefone;
    @Column(name = "email", nullable = true, updatable = true, length = 100)
    private String email;

    public Fornecedor(Long id, String razao_social, String cnpj, String nome_fantasia, String endereco, String telefone, String email) {
        this.id = id;
        this.razao_social = razao_social;
        this.CNPJ = cnpj;
        this.nome_fantasia = nome_fantasia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public Fornecedor(String razao_social, String cnpj, String nome_fantasia, String endereco, String telefone, String email) {
    }

    public Fornecedor() {

    }
    public Long getId() {return id; }

    public String getRazao_social() {
        return razao_social;
    }
    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getCNPJ() {
        return CNPJ;
    }
    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
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

    public String getTelefone() {return telefone; }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}