package br.com.hbsis.Funcionario;

import br.com.hbsis.Api.ApiService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);
    private final IFuncionarioRepository iFuncionarioRepository;
    private final ApiService apiService;

    @Autowired
    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository, ApiService apiService) {
        this.iFuncionarioRepository = iFuncionarioRepository;
        this.apiService = apiService;
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Salvando o funcionário!");
        LOGGER.debug("Funcionário: {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();

        funcionario.setId(funcionarioDTO.getId());
        funcionario.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setUuid(apiService.getUuid(funcionarioDTO.getNomeFuncionario()));

        this.validate(funcionarioDTO);
        funcionario = this.iFuncionarioRepository.save(funcionario);
        return FuncionarioDTO.of(funcionario);
    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando Funcionário!");
        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("Funcionário não deve ser nulo!");
        }
        if (StringUtils.isEmpty(funcionarioDTO.getNomeFuncionario())) {
            throw new IllegalArgumentException("Nome do funcionário não deve ser vazio ou nulo!");
        }
        if (StringUtils.isEmpty(funcionarioDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail não pode ser vazio ou nulo!");
        }
        if (StringUtils.isEmpty(funcionarioDTO.getUuid())) {
            throw new IllegalArgumentException("Uuid não pode ser vazio ou nulo!");
        }
    }

    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, Long id) {
        Optional<Funcionario> funcionarioExistenteOptional = this.iFuncionarioRepository.findById(id);

        if (funcionarioExistenteOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioExistenteOptional.get();
            LOGGER.info("Atualizando o funcionário... ID: [{}]", funcionarioExistente.getId());
            LOGGER.info("Payload: {}", funcionarioExistente);

            funcionarioExistente.setId(funcionarioDTO.getId());
            funcionarioExistente.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
            funcionarioExistente.setEmail(funcionarioDTO.getEmail());

            this.validate(funcionarioDTO);
            LOGGER.info("Validando o funcionário!");
            funcionarioExistente = this.iFuncionarioRepository.save(funcionarioExistente);
            return funcionarioDTO.of(funcionarioExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe!", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para o funcionário de ID: [{}]", id);
        this.iFuncionarioRepository.deleteById(id);
    }

    public FuncionarioDTO findById(Long id) {
        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);
        if (funcionarioOptional.isPresent()) {
            return FuncionarioDTO.of(funcionarioOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe!", id));
    }
}