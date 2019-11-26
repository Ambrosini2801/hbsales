package br.com.hbsis.Fornecedor;

public class FornecedorDTO {
    private Long id;
    private String razao_social;
    private String cnpj;
    private String nome_fantasia;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO (){

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
                    fornecedor.getRazao_social(),
                    fornecedor.getCNPJ(),
                    fornecedor.getNome_fantasia(),
                    fornecedor.getEndereco(),
                    fornecedor.getTelefone(),
                    fornecedor.getEmail()
        );
    }

    public Long setId() {
        return id;
    }

    public String setRazao_social() {
        return razao_social;
    }

    public String setCNPJ() {
        return cnpj;
    }

    public String setNome_fantasia() {
        return nome_fantasia;
    }

    public String setEndereco() {
        return endereco;
    }

    public String setTelefone() {
        return telefone;
    }

    public String setEmail() {
        return email;
    }

    public void getRazao_social() {
    }

    public void getCNPJ() {
    }

    public void getNome_fantasia() {
    }

    public void getEndereco() {
    }

    public void getTelefone() {
    }

    public void getEmail() {
    }

    }