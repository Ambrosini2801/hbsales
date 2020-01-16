package br.com.hbsis.Item;

import br.com.hbsis.Api.ApiService;
import br.com.hbsis.Api.Invoice.InvoiceDTO;
import br.com.hbsis.Api.Invoice.InvoiceItemDTO;
import br.com.hbsis.Pedido.Pedido;
import br.com.hbsis.Pedido.PedidoService;
import br.com.hbsis.Produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final ApiService apiService;
    private final IItemRepository iItemRepository;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    @Autowired
    public ItemService(ApiService apiService, IItemRepository iItemRepository, ProdutoService produtoService, PedidoService pedidoService) {
        this.apiService = apiService;
        this.iItemRepository = iItemRepository;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
    }

    public ItemDTO save(ItemDTO itemDTO) {
        LOGGER.info("Salvando item!");
        LOGGER.debug("Item: {}", itemDTO.getProduto());

        Item item = new Item();
        item.setPreco(itemDTO.getPreco());
        item.setId(itemDTO.getId());
        item.setQuantidade(itemDTO.getQuantidade());
        item.setProduto(produtoService.findProdutoById(itemDTO.getProduto()));
        item.setPedido(pedidoService.findPedidoByid(itemDTO.getPedido()));
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

        if (StringUtils.isEmpty(String.valueOf(itemDTO.getProduto()))) {
            throw new IllegalArgumentException("Produto não deve ser nulo ou vazio!");
        }
    }

    public ItemDTO update(ItemDTO itemDTO, Long id) {
        Optional<Item> itemOptional = this.iItemRepository.findById(id);

        if (itemOptional.isPresent()) {
            Item itemExistente = itemOptional.get();

            LOGGER.info("Atualizando item... id: [{}]", itemDTO.getProduto());
            LOGGER.debug("Payload: {}", itemDTO);
            LOGGER.debug("Item Existente: {}", itemExistente);
            itemExistente.setId(itemDTO.getId());
            itemExistente.setQuantidade(itemDTO.getQuantidade());
            itemExistente.setProduto(produtoService.findProdutoById(itemDTO.getProduto()));

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

    public List<Item> listItens(Long idPedido) {
        List<Item> listaItens = iItemRepository.findItensByPedido(idPedido);
        if (listaItens != null) {
            return listaItens;
        } else throw new IllegalArgumentException("Não há itens para esse pedido");
    }

    public Number totalValue(Long idPedido) {
        List<Item> listaItens = this.listItens(idPedido);
        double totalValue = 0;
        Number total = 0;
        for (Item item : listaItens) {
            LOGGER.info(item.getProduto().getNomeProduto());
            totalValue = totalValue + (item.getPreco() * item.getQuantidade());
            LOGGER.info(String.valueOf(totalValue));
        }
        total = totalValue;
        LOGGER.info(total.toString());

        return total;
    }

    public Set<InvoiceItemDTO> setInvoiceItemDTOSet(Long idPedido) {
        Set<InvoiceItemDTO> invoiceItemDTOSet = new HashSet<>();
        List<Item> listaItens = this.listItens(idPedido);
        for (Item item : listaItens) {
            LOGGER.info(String.valueOf(item.getQuantidade()));
            LOGGER.info(String.valueOf(item.getProduto().getNomeProduto()));
            invoiceItemDTOSet.add(new InvoiceItemDTO(item.getQuantidade(), item.getProduto().getNomeProduto()));

        }
        return invoiceItemDTOSet;
    }

    public InvoiceDTO setInvoiceDTO(Long idPedido){
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        Pedido pedido = pedidoService.findPedidoByid(idPedido);
        LOGGER.info(pedido.getFornecedor().getCNPJ());
        LOGGER.info(pedido.getUuid());
        invoiceDTO.setCnpjFornecedor(pedido.getFornecedor().getCNPJ());
        invoiceDTO.setEmployeeUuid(pedido.getUuid());
        invoiceDTO.setTotalValue(this.totalValue(idPedido));
        invoiceDTO.setInvoiceItemDTOSet(this.setInvoiceItemDTOSet(idPedido));
        return invoiceDTO;
    }
    public String validacaoApi(Long idPedido) throws IOException {
        String resultado = apiService.sendValidacao(this.setInvoiceDTO(idPedido));
        return resultado;


    }
}