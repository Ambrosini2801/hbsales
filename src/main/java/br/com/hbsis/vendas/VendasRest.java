package br.com.hbsis.vendas;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendas")
public class VendasRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(VendasRest.class);
    private VendasService VendasService = null;

    @Autowired
    public VendasRest(VendasService vendasService) {
        this.VendasService = vendasService;
    }

    @PostMapping
    public VendasDTO save(@RequestBody VendasDTO vendasDTO) {
        LOGGER.info("Receber solicitação de vendas!");
        LOGGER.debug("Payload: {}", vendasDTO);
        return this.VendasService.save(vendasDTO);
    }

    @GetMapping
    public VendasDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.VendasService.findByid(id);
    }

    @PutMapping
    public VendasDTO update(@PathVariable("id") Long id, @RequestBody VendasDTO vendasDTO) {
        LOGGER.info("Recebendo update para vendas de ID: {}", id);
        LOGGER.info("Payload: {}", vendasDTO);
        return this.VendasService.update(vendasDTO, id);
    }

    @DeleteMapping
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo delete para vendas de ID: {}", id);
        this.VendasService.delete(id);
    }
}