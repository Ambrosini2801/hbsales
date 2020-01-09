package br.com.hbsis.Pedido;

import br.com.hbsis.Api.ApiService;
import br.com.hbsis.Fornecedor.FornecedorService;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoService;
import br.com.hbsis.vendas.Vendas;
import br.com.hbsis.vendas.VendasService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.DesktopBrowse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);
    private final ApiService apiService;
    private final IPedidoRepository iPedidoRepository;
    private final FornecedorService fornecedorService;
    private final ProdutoService produtoService;
    private final VendasService vendasService;

    @Autowired
    public PedidoService(ApiService apiService, IPedidoRepository iPedidoRepository, FornecedorService fornecedorService, ProdutoService produtoService, VendasService vendasService) {
        this.apiService = apiService;
        this.iPedidoRepository = iPedidoRepository;
        this.fornecedorService = fornecedorService;
        this.produtoService = produtoService;
        this.vendasService = vendasService;

    }

    public PedidoDTO save(PedidoDTO pedidoDTO) {
        LOGGER.info("Salvando pedido!");
        LOGGER.debug("Pedido: {}", pedidoDTO.getCodigo());

        Pedido pedido = new Pedido();

        pedido.setCodPedido(pedidoDTO.getCodigo());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setQuantidade(pedidoDTO.getQuantidade());
        pedido.setUuid(pedidoDTO.getUuid());
        pedido.setDataPedido(pedidoDTO.getDataPedido());
        pedido.setFornecedor(fornecedorService.findFornecedorById1(pedidoDTO.getFornecedor()));
        pedido.setProduto(produtoService.findProdutoById(pedidoDTO.getProduto()));
        pedido.setVendas(vendasService.findVendasById(pedidoDTO.getVendas()));

        this.validate(pedidoDTO);
        pedido = this.iPedidoRepository.save(pedido);

        return PedidoDTO.of(pedido);
    }

    private void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando produto");
        if (pedidoDTO == null) {
            throw new IllegalArgumentException("Pedido não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(pedidoDTO.getCodigo().toUpperCase())) {
            throw new IllegalArgumentException("Código do pedido não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getStatus()))) {
            throw new IllegalArgumentException("O STATUS não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getQuantidade()))) {
            throw new IllegalArgumentException("Quantidade de produto não deve ser nula ou vazia!");
        }

        if (StringUtils.isEmpty(pedidoDTO.getUuid())) {
            throw new IllegalArgumentException("Uuid de produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getDataPedido()))) {
            throw new IllegalArgumentException("A data do pedido não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getFornecedor()))) {
            throw new IllegalArgumentException("Fornecedor não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getProduto()))) {
            throw new IllegalArgumentException("O produto não deve ser nulo ou vazio!");
        }

        Produto produto;
        produto = produtoService.findProdutoById(pedidoDTO.getProduto());
        Vendas vendas = vendasService.findVendasById(pedidoDTO.getVendas());

        if (produto.getCategoriaLinha().getCategoria().getFornecedor().getId() != vendas.getFornecedor().getId()) {
            throw new IllegalArgumentException("O produto não pertence a este fornecedor!");
        }

    }

//    public boolean data(String data) {
//        LOGGER.info("Validando data de pedidos!");
//        try {
//            SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
//            formatdata.setLenient(false);
//            formatdata.parse(data);
//            return true;
//        } catch (ParseException ex) {
//            return false;
//        }
//    }

//    public boolean dataMenorQueHoje(String data) {
//        LOGGER.info("Verificando se a data está correta!");
//        DateTimeFormatter dataMenor = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDate dataOk = LocalDate.parse(data, dataMenor); //Pega a data de hoje
//        LocalDate dataHoje = LocalDate.now(); //Verifica se a data é menor ou igual a zero então retorna verdadeiro, senão retorna falso
//        LocalDate dataPedido = LocalDate.now();
//
//        if (dataHoje.compareTo(dataMenorQueHoje())) {
//            throw new IllegalArgumentException("Esse período não existe");
//        } else if (!dataHoje.isAfter(dataHoje)) {
//            throw new IllegalArgumentException("Esse período ainda não começou...");
//        } else if (!dataHoje.isBefore(dataOk)) {
//            throw new IllegalArgumentException("Esse período já terminou...");
//        }
//
//        return dataOk.compareTo(dataHoje) <= 0; //quando a data1 é menor isso retorna -1, quando é maior retorna 1, e quando é igual retorna 0
//    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoExistenteOptional = this.iPedidoRepository.findById(id);

        if (pedidoExistenteOptional.isPresent()) {
            Pedido pedidoExistente = pedidoExistenteOptional.get();

            LOGGER.info("Atualizando pedido... id: [{}]", pedidoDTO.getCodigo());
            LOGGER.debug("Payaload: {}", pedidoDTO);
            LOGGER.debug("Pedido Existente: {}", pedidoExistente);

            pedidoExistente.setCodPedido(pedidoDTO.getCodigo().toUpperCase());
            pedidoExistente.setStatus(pedidoDTO.getStatus());
            pedidoExistente.setQuantidade(pedidoDTO.getQuantidade());
            pedidoExistente.setUuid(pedidoDTO.getUuid());
            pedidoExistente.setDataPedido(pedidoDTO.getDataPedido());
            pedidoExistente.setFornecedor(fornecedorService.findFornecedorById1(pedidoDTO.getFornecedor()));
            pedidoExistente.setProduto(produtoService.findProdutoById(pedidoDTO.getProduto()));
            pedidoExistente.setVendas(vendasService.findVendasById(pedidoDTO.getVendas()));

            pedidoExistente = this.iPedidoRepository.save(pedidoExistente);
            return pedidoDTO.of(pedidoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe!", id));
    }

    public PedidoDTO findByid(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            return PedidoDTO.of(pedidoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe!", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para o pedido de ID: [{]}", id);
        this.iPedidoRepository.deleteById(id);
    }
}