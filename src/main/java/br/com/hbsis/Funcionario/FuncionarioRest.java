package br.com.hbsis.Funcionario;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/funcionarios")
public class FuncionarioRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioRest.class);
    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRest(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public FuncionarioDTO save(@RequestBody FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Recebendo solicitação de persistências de funcionário...");
        LOGGER.debug("Payload: {}", funcionarioDTO);
        return this.funcionarioService.save(funcionarioDTO);
    }

    @GetMapping("/{id}")
    public FuncionarioDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... ID: [{}]", id);
        return this.funcionarioService.findById(id);
    }

    @PutMapping("/{id}")
    public FuncionarioDTO update(@PathVariable("id") Long id, @RequestBody FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Recebendo Update para o funcionário de ID: {}", id);
        LOGGER.debug("Payload: {}", funcionarioDTO);
        return this.funcionarioService.update(funcionarioDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo delete para o funcionário de ID: {}", id);
        this.funcionarioService.delete(id);
    }
}