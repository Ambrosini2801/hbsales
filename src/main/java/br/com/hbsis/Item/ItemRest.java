package br.com.hbsis.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ItemDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.itemService.findById(id);
    }

    @PutMapping
    public ItemDTO update(@PathVariable("id") Long id, @RequestBody ItemDTO itemDTO) {
        LOGGER.info("Recebendo update para o item de ID: {}", id);
        LOGGER.info("Payload {}", itemDTO);
        return this.itemService.update(itemDTO, id);
    }

    @DeleteMapping
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo delete para o pedido de ID: {}", id);
        this.itemService.delete(id);
    }
}