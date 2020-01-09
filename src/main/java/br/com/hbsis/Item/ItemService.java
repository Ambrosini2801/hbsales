package br.com.hbsis.Item;

import br.com.hbsis.Api.ApiService;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final ApiService apiService;
    private final IItemRepository iItemRepository;
    private final ProdutoService produtoService;

    @Autowired
    public ItemService(ApiService apiService, IItemRepository iItemRepository, ProdutoService produtoService) {
        this.apiService = apiService;
        this.iItemRepository = iItemRepository;
        this.produtoService = produtoService;
    }

    public ItemDTO save(ItemDTO itemDTO) {
        LOGGER.info("Salvando item!");
        LOGGER.debug("Item: {}", itemDTO.getProduto());

        Item item = new Item();

        item.setId(itemDTO.getId());
        item.setQuantidade(itemDTO.getQuantidade());
        item.setProduto(produtoService.findProdutoById(itemDTO.getProduto().getId()));

        this.validate(itemDTO);
        item = this.iItemRepository.save(item);

        return ItemDTO.of(item);
    }

    private void validate(ItemDTO itemDTO) {
        LOGGER.info("Validando Item!");
        if (itemDTO == null) {
            throw new IllegalArgumentException("Item não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(itemDTO.getQuantidade()))) {
            throw new IllegalArgumentException("Quantidade do item não deve ser nula ou vazia!");
        }

        if (StringUtils.isEmpty(String.valueOf(itemDTO.getProduto().getId()))) {
            throw new IllegalArgumentException("Produto não deve ser nulo ou vazio!");
        }
    }

    public ItemDTO update(ItemDTO itemDTO, Long id) {
        Optional<Item> itemOptional = this.iItemRepository.findById(id);

        if (itemOptional.isPresent()) {
            Item itemExistente = itemOptional.get();

            LOGGER.info("Atualizando item... id: [{}]", itemDTO.getProduto());
            LOGGER.debug("Payaload: {}", itemDTO);
            LOGGER.debug("Item Existente: {}", itemExistente);

            itemExistente.setId(itemDTO.getId());
            itemExistente.setQuantidade(itemDTO.getQuantidade());
            itemExistente.setProduto(produtoService.findProdutoById(itemDTO.getProduto().getId()));

            itemExistente = this.iItemRepository.save(itemExistente);
            return itemDTO.of(itemExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe!", id));
    }

    public ItemDTO findById(Long id) {
        Optional<Item> itemOptional = this.iItemRepository.findById(id);

        if (itemOptional.isPresent()) {
            return ItemDTO.of(itemOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe!", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para o item de ID: [{}]", id);
        this.iItemRepository.deleteById(id);
    }
}