package br.com.hbsis.Fornecedor;

import javax.persistence.*;

@Entity
@Table(name = "cad_fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_social", unique = true, nullable = true, length = 100)
    private String razaoSocial;
    @Column(name = "cnpj", unique = true, nullable = true, length = 14)
    private String CNPJ;
    @Column(name = "nome_fantasia", nullable = true, updatable = true, length = 100)
    private String nome_fantasia;
    @Column(name = "endereco", nullable = true, updatable = true, length = 100)
    private String endereco;
    @Column(name = "telefone", nullable = true, updatable = true, length = 50)
    private String telefone;
    @Column(name = "email", nullable = true, updatable = true, length = 100)
    private String email;

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


    @Override
    public String toString() {
        return "categoria [razao_social = " + razaoSocial + ", cnpj =" + CNPJ + " ," +
                " nome_fantasia = " + nome_fantasia + ", endereco = " + endereco + "telefone = " + telefone + "]";


    }


}
