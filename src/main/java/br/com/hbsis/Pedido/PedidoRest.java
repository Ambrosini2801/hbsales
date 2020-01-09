package br.com.hbsis.Pedido;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoRest.class);
    private PedidoService PedidoService;

    @Autowired
    public PedidoRest(PedidoService pedidoService) {
        this.PedidoService = pedidoService;
    }

    @PostMapping("/{id}")
    public PedidoDTO save(@RequestBody PedidoDTO pedidoDTO) {
        LOGGER.info("Receber solicitação do pedido!");
        LOGGER.debug("Payload: {}", pedidoDTO);
        return this.PedidoService.save(pedidoDTO);
    }

    @GetMapping("/{id}")
    public PedidoDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.PedidoService.findByid(id);
    }

    @PutMapping("/{id}")
    public PedidoDTO update(@PathVariable("id") Long id, @RequestBody PedidoDTO pedidoDTO) {
        LOGGER.info("Recebendo Update para o pedido de ID: {}", id);
        LOGGER.info("Payload: {}", pedidoDTO);
        return this.PedidoService.update(pedidoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo delete para o pedido de ID: {}", id);
        this.PedidoService.delete(id);
    }
}