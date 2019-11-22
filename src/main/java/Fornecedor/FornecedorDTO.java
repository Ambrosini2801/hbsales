package Fornecedor;

public class FornecedorDTO {
    private Long id;
    private String razao_social;
    private String CNPJ;
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

    public FornecedorDTO(Long id, String razao_social, String CNPJ, String nome_fantasia, String endereco, String telefone, String email) {
        this.id = id;
        this.razao_social = razao_social;
        this.CNPJ = CNPJ;
        this.nome_fantasia = nome_fantasia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public FornecedorDTO(Long id, String getrazao_social, String nome_fantasia, String endereco, String telefone, String email) {
    }

    public FornecedorDTO(Long id, String getrazao_social, String nome_fantasia, String endereco, Long telefone, String email) {
    }

    public static FornecedorDTO of(Fornecedor Fornecedor) {
      		return new FornecedorDTO(
      		        Fornecedor.getId(),
         			Fornecedor.getrazao_social(),
         			Fornecedor.getNome_fantasia(),
         			Fornecedor.getEndereco(),
         			Fornecedor.getTelefone(),
         			Fornecedor.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
}