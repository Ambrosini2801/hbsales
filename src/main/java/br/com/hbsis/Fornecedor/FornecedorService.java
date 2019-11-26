package br.com.hbsis.Fornecedor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);
    private final IFornecedorRepository iFornecedorRepository;
    private Fornecedor Fornecedor;

    @Autowired
    public FornecedorService(IFornecedorRepository iFornecedorRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
    }
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = new Fornecedor(

                fornecedorDTO.setRazao_social(),
                fornecedorDTO.setCNPJ(),
                fornecedorDTO.setNome_fantasia(),
                fornecedorDTO.setEndereco(),
                fornecedorDTO.setTelefone(),
                fornecedorDTO.setEmail()

        );

        Fornecedor save = this.iFornecedorRepository.save(fornecedor);
        return FornecedorDTO.of(fornecedor);
    }
    private void validate(FornecedorDTO FornecedorDTO) {
        LOGGER.info("Validando Fornecedor");
        if (FornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }
    }
    public FornecedorDTO findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public FornecedorDTO update(FornecedorDTO FornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();
            LOGGER.info("Atualizando usuário... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payload: {}", FornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorExistente = this.iFornecedorRepository.save(fornecedorExistente);
            return FornecedorDTO.of(fornecedorExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para fornecedor de ID: [{}]", id);
        this.iFornecedorRepository.deleteById(id);
    }

}

