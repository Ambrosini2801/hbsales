package br.com.hbsis.Api.Invoice;

import br.com.hbsis.Api.ApiService;
import br.com.hbsis.Item.ItemService;
import br.com.hbsis.Pedido.Pedido;
import br.com.hbsis.Pedido.PedidoDTO;
import br.com.hbsis.Pedido.PedidoService;
import br.com.hbsis.Produto.ProdutoService;

import java.io.IOException;

public class InvoiceService {

    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final ItemService itemService;
    private final ApiService apiService;

    public InvoiceService(PedidoService pedidoService, ProdutoService produtoService, ItemService itemService, ApiService apiService) {
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;
        this.itemService = itemService;
        this.apiService = apiService;
    }

    public InvoiceDTO setInvoiceItemDTOSet(Long idPedido) throws IOException {

        PedidoDTO pedidoDTO = new PedidoDTO();
        Pedido pedido = pedidoService.findPedidoByid(idPedido, pedidoDTO);
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setEmployeeUuid(pedido.getUuid());
        invoiceDTO.setCnpjFornecedor(pedido.getFornecedor().getCNPJ());

        return invoiceDTO;
    }
}