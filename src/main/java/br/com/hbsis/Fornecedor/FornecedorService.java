package br.com.hbsis.Fornecedor;

import com.microsoft.sqlserver.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);
    private final IFornecedorRepository iFornecedorRepository;

    @Autowired
    public FornecedorService(IFornecedorRepository iFornecedorRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Salvando o fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setCNPJ(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        this.validate(fornecedorDTO);
        fornecedor = this.iFornecedorRepository.save(fornecedor);
        return FornecedorDTO.of(fornecedor);
    }

    private void validate(FornecedorDTO FornecedorDTO) {
        LOGGER.info("Validando Fornecedor");
        if (FornecedorDTO == null) {
            throw new IllegalArgumentException("Fornecedor não deve ser nulo!");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getRazaoSocial())) {
            throw new IllegalArgumentException("Razão Social não deve ser nula ou vazia!");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ não deve ser nulo ou vazio!");
        }
        if (!(StringUtils.isNumeric(FornecedorDTO.getCnpj()))) {
            throw new IllegalArgumentException("CNPJ deve ser conter apenas números!");
        }
        if (FornecedorDTO.getCnpj().length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter 14 dígitos");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getNomeFantasia())) {
            throw new IllegalArgumentException("Nome Fantasia não deve ser nula ou vazia!");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getEndereco())) {
            throw new IllegalArgumentException("Endereço não deve ser nulo ou vazio!");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getTelefone())) {
            throw new IllegalArgumentException("Telefone não deve ser nulo ou vazio!");
        }
        if (!(StringUtils.isNumeric(FornecedorDTO.getTelefone()))) {
            throw new IllegalArgumentException("Telefone não pode conter letras!");
        }
        if (Integer.parseInt(String.valueOf(FornecedorDTO.getTelefone().charAt(4))) != 9) {
            throw new IllegalArgumentException("Somente números de telefone celular!");
        }
        if (FornecedorDTO.getTelefone().length() != 13) {
            throw new IllegalArgumentException("Numeração incorreta!");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail não deve ser nulo ou vazio!");
        }
    }

    public FornecedorDTO findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public FornecedorDTO findFornecedorById(long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor findFornecedorById1(long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();
            LOGGER.info("Atualizando o Fornecedor... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payaload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorExistente.setRazaoSocial((fornecedorDTO.getRazaoSocial()));
            fornecedorExistente.setCNPJ(fornecedorDTO.getCnpj());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());

            this.validate(fornecedorDTO);

            LOGGER.info("Validando o Fornecedor!");

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