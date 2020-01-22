package br.com.hbsis.Pedido;

import br.com.hbsis.Email.MailConfig;
import br.com.hbsis.Fornecedor.FornecedorService;
import br.com.hbsis.Funcionario.Funcionario;
import br.com.hbsis.Funcionario.FuncionarioDTO;
import br.com.hbsis.Funcionario.FuncionarioService;
import br.com.hbsis.Funcionario.IFuncionarioRepository;
import br.com.hbsis.Item.Item;
import br.com.hbsis.Item.ItemService;
import br.com.hbsis.Vendas.Vendas;
import br.com.hbsis.Vendas.VendasService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);
    private final IPedidoRepository iPedidoRepository;
    private final FornecedorService fornecedorService;
    private final VendasService vendasService;
    private final ItemService itemService;
    private final FuncionarioService funcionarioService;
    private final MailConfig mailConfig;
    private final IFuncionarioRepository iFuncionarioRepository;

    @Autowired
    public PedidoService(IPedidoRepository iPedidoRepository, FornecedorService fornecedorService, VendasService vendasService, @Lazy ItemService itemService, FuncionarioService funcionarioService, MailConfig mailConfig, IFuncionarioRepository iFuncionarioRepository) {
        this.iPedidoRepository = iPedidoRepository;
        this.fornecedorService = fornecedorService;
        this.vendasService = vendasService;
        this.itemService = itemService;
        this.funcionarioService = funcionarioService;
        this.mailConfig = mailConfig;
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO) {
        LOGGER.info("Salvando pedido!");
        LOGGER.debug("Pedido: {}", pedidoDTO.getCodigo());

        Pedido pedido = new Pedido();
        pedido.setCodPedido(pedidoDTO.getCodigo());
        pedido.setStatus(pedidoDTO.getStatus(EnumStatusPedido.CANCELADO));
        pedido.setUuid(pedidoDTO.getUuid());
        pedido.setDataPedido(pedidoDTO.getDataPedido());
        pedido.setFornecedor(fornecedorService.findFornecedorById1(pedidoDTO.getFornecedor()));
        pedido.setVendas(vendasService.findVendasById(pedidoDTO.getVendas()));

        this.validate(pedidoDTO);
        pedido = this.iPedidoRepository.save(pedido);
        mailConfig.sendMailSave();

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

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getStatus(EnumStatusPedido.CANCELADO)))) {
            throw new IllegalArgumentException("O STATUS não deve ser nulo ou vazio!");
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
    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoExistenteOptional = this.iPedidoRepository.findById(id);

        if (pedidoExistenteOptional.isPresent()) {
            Pedido pedidoExistente = pedidoExistenteOptional.get();

            LOGGER.info("Atualizando pedido... id: [{}]", pedidoDTO.getCodigo());
            LOGGER.debug("Payload: {}", pedidoDTO);
            LOGGER.debug("Pedido Existente: {}", pedidoExistente);

            pedidoExistente.setCodPedido(pedidoDTO.getCodigo().toUpperCase());
            pedidoExistente.setStatus(pedidoDTO.getStatus(EnumStatusPedido.CANCELADO));
            pedidoExistente.setUuid(pedidoDTO.getUuid());
            pedidoExistente.setDataPedido(pedidoDTO.getDataPedido());
            pedidoExistente.setFornecedor(fornecedorService.findFornecedorById1(pedidoDTO.getFornecedor()));
            pedidoExistente.setVendas(vendasService.findVendasById(pedidoDTO.getVendas()));

            pedidoExistente = this.iPedidoRepository.save(pedidoExistente);
            return PedidoDTO.of(pedidoExistente);
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

    public Pedido findPedidoByid(Long id, PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            return pedidoOptional.get();
        } else return null;
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para o pedido de ID: [{]}", id);
        this.iPedidoRepository.deleteById(id);
    }

    public String mascaraCNPJ(String cnpj) {
        try {
            MaskFormatter format = new MaskFormatter("##.###.###/####-##");
            format.setValueContainsLiteralCharacters(false);
            return format.valueToString(cnpj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pedido> findByVenda(Vendas vendas) {
        List<Pedido> pedidoList = new ArrayList<>();
        try {
            pedidoList = iPedidoRepository.findByVendas(vendas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidoList;
    }

    public void relatorioCSV(HttpServletResponse relatorioCSV, Long id) throws IOException {

        String nomeRelatorio = "relatorio.csv";
        relatorioCSV.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + nomeRelatorio + "\"");
        relatorioCSV.setContentType("text/csv");
        PrintWriter writer = relatorioCSV.getWriter();

        String lista = ("nome_produto; quantidade; razao_social; cnpj");
        writer.write(lista);
        Vendas vendas = vendasService.findByIdPeriodo(id);
        List<Item> itemList;
        List<Pedido> pedidoList;
        pedidoList = findByVenda(vendas);

        for (Pedido pedido : pedidoList) {
            itemList = itemService.findByItemPedido(pedido);
            for (Item item : itemList) {
                writer.write("\n");

                writer.append(item.getProduto().getNomeProduto() + ";" +
                        item.getQuantidade() + ";" +
                        item.getPedido().getFornecedor().getRazaoSocial() + ";" +
                        mascaraCNPJ(item.getPedido().getVendas().getFornecedor().getCNPJ()));

                writer.flush();
            }
        }
    }

    private List<Pedido> findByfuncionario(FuncionarioDTO funcionario) {
        List<Pedido> pedidoLista = new ArrayList<>();
        try {
            pedidoLista = iPedidoRepository.findById(funcionario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidoLista;
    }

    public void relatorioVendas(HttpServletResponse relatorioVendas, Long id) throws IOException {

        String nomeCSV = "relatorioVendas.csv";
        relatorioVendas.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + nomeCSV + "\"");
        relatorioVendas.setContentType("text/csv");
        PrintWriter writerVendas = relatorioVendas.getWriter();

        String listaVendas = ("nome_funcionario; nome_produto; quantidade; razao_social; cnpj");
        writerVendas.write(listaVendas);
        FuncionarioDTO funcionario = funcionarioService.findById(id);
        List<Item> itemLista;
        List<Pedido> pedidoLista;
        pedidoLista = findByfuncionario(funcionario);

        for (Pedido pedido : pedidoLista) {
            itemLista = itemService.findByItemPedido(pedido);
            for (Item itens : itemLista) {
                writerVendas.write("\n");

                writerVendas.append(funcionario.getNomeFuncionario() + ";" +
                        itens.getProduto().getNomeProduto() + ";" +
                        itens.getQuantidade() + ";" +
                        itens.getPedido().getFornecedor().getRazaoSocial() + ";" +
                        mascaraCNPJ(itens.getPedido().getVendas().getFornecedor().getCNPJ()));

                writerVendas.flush();
            }
        }
    }

    public List<Pedido> visualizarPedidos(Long id) {
        List<Pedido> listPedido = iPedidoRepository.findAll();
        Funcionario funcionario = this.iFuncionarioRepository.findByid(id);

        for (Pedido pedido : listPedido)
            if (funcionario.equals(id) || pedido.getId().equals(id)) {

                if (pedido.getStatus().equals(EnumStatusPedido.ABERTO) || pedido.getStatus().equals(EnumStatusPedido.RETIRADO)) {
                    pedido.setId(pedido.getId());
                    pedido.setCodPedido(pedido.getCodPedido());
                    pedido.setStatus(pedido.getStatus());
                    pedido.setDataPedido(pedido.getDataPedido());
                    listPedido.add(pedido);

                } else {
                    throw new IllegalArgumentException("Não foi possível visualizar este pedido!");
                }
            }
        return listPedido;
    }

    public PedidoDTO cancelarPedido(Long id) {
        Pedido pedido = this.iPedidoRepository.findByid(id);
        Funcionario funcionario = this.iFuncionarioRepository.findByid(id);
        PedidoDTO pedidoDTO = PedidoDTO.of(pedido);

        pedidoDTO.getStatus(EnumStatusPedido.CANCELADO);

        if (funcionario.equals(id) || pedido.getId().equals(id))

            if (pedido.getStatus().equals(EnumStatusPedido.ABERTO)) {
                if (pedido.getVendas().getFimVendas().isBefore(LocalDate.now()) || pedido.getVendas().getFimVendas().isAfter(LocalDate.now())) {

                } else {
                    throw new IllegalArgumentException("Não foi possível cancelar este pedido!");
                }
            }
        return pedidoDTO;
    }

    public PedidoDTO editarPedido(Long id) {
        Pedido pedido = this.iPedidoRepository.findPedidoByid(id);
        PedidoDTO pedidoDTO = PedidoDTO.of(pedido);

            if (pedido.getStatus().equals(EnumStatusPedido.ABERTO)) {
                if (pedido.getVendas().getInicioVendas().isBefore(LocalDate.now()) && pedido.getVendas().getFimVendas().isAfter(LocalDate.now())) {
                } else {
                    throw new IllegalArgumentException("Não foi possível editar este pedido!");
                }
            }
        return pedidoDTO;
    }

    public PedidoDTO retirarPedido(Long id) {
        Pedido pedido = this.iPedidoRepository.findPedidoByid(id);
        PedidoDTO pedidoDTO = PedidoDTO.of(pedido);

            if (pedido.getVendas().getRetiradaPedido().equals(LocalDate.now())) {
                if (pedido.getStatus().equals(EnumStatusPedido.ABERTO)) {
                    pedido.setStatus(EnumStatusPedido.RETIRADO);
                    this.update(PedidoDTO.of(pedido), id);
                } else {
                    throw new IllegalArgumentException("Retirada inválido!");
                }
            }
        return pedidoDTO;
    }
}