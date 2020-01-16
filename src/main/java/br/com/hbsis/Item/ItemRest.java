package br.com.hbsis.Item;

import br.com.hbsis.Api.Invoice.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/item")
public class ItemRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRest.class);
    private ItemService itemService;

    @Autowired
    public ItemRest(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDTO save(@RequestBody ItemDTO itemDTO) {
        LOGGER.info("Receber solicitação do item!");
        LOGGER.debug("Payload: {}", itemDTO);
        return this.itemService.save(itemDTO);
    }

    @GetMapping("/{id}")
    public ItemDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.itemService.findById(id);
    }

    @PutMapping("/{id}")
    public ItemDTO update(@PathVariable("id") Long id, @RequestBody ItemDTO itemDTO) {
        LOGGER.info("Recebendo update para o item de ID: {}", id);
        LOGGER.info("Payload {}", itemDTO);
        return this.itemService.update(itemDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo delete para o pedido de ID: {}", id);
        this.itemService.delete(id);
    }
    @GetMapping("/totalvalue/{idPedido}")
    public InvoiceDTO totalValue(@PathVariable("idPedido") Long idPedido) {
        LOGGER.info("Recebendo find by ID... id: [{}]", idPedido);
        return (InvoiceDTO) this.itemService.setInvoiceItemDTOSet(idPedido);
    }

    @GetMapping("/validacaoApi/{idPedido}")
    public String validacaoApi(@PathVariable("idPedido") Long idPedido) throws IOException {
        LOGGER.info("Recebendo find by ID... id: [{}]", idPedido);
        return this.itemService.validacaoApi(idPedido);
    }
}