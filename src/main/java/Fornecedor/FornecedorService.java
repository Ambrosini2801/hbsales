package Fornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {
    private final IFornecedorRepository iFornecedorRepository;
    private Fornecedor Fornecedor;

    @Autowired
    public FornecedorService(IFornecedorRepository iFornecedorRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public FornecedorDTO save (FornecedorDTO fornecedorDTO){
        Fornecedor fornecedor = new Fornecedor (
                fornecedorDTO.getRazao_social(),
                fornecedorDTO.getCNPJ(),
                fornecedorDTO.getNome_fantasia(),
                fornecedorDTO.getEndereco(),
                fornecedorDTO.getTelefone(),
                fornecedorDTO.getEmail()
        );

       Fornecedor = this.iFornecedorRepository.save(Fornecedor);

       return FornecedorDTO.of(fornecedor);
    }
    public IFornecedorRepository getiFornecedorRepository() {
        return iFornecedorRepository;
    }
}
