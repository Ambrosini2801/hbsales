package br.com.hbsis.Fornecedor;

public class FornecedorDTO {
    private Long id;
    private String razao_social;
    private String cnpj;
    private String nome_fantasia;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long id, String razao_social) {
        this.id = id;
        this.nome_fantasia = razao_social;
    }

    public FornecedorDTO(Long id, String razao_social, String cnpj, String nome_fantasia, String endereco, String telefone, String email) {
        this.id = id;
        this.razao_social = razao_social;
        this.cnpj = cnpj;
        this.nome_fantasia = nome_fantasia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getRazaoSocial(),
                fornecedor.getCNPJ(),
                fornecedor.getNome_fantasia(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getEmail()
        );
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getRazao_social() {return razao_social;}
    public void setRazao_social(String razao_social) {this.razao_social = razao_social;}

    public String getCnpj() {return cnpj;}
    public void setcnpj(String cnpj) {this.cnpj = cnpj;}

    public String getNome_fantasia() {return nome_fantasia;}
    public void setNome_fantasia(String nome_fantasia) {this.nome_fantasia = nome_fantasia;}

    public String getEndereco() {return endereco;}
    public void setendereco(String endereco) {this.endereco = endereco;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public String getEmail() {return email;}
    public void setemail(String email) {this.email = email;}

}



