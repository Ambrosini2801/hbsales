package br.com.hbsis.Pedido;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);
    private final IPedidoRepository iPedidoRepository;

    @Autowired
    public PedidoService(IPedidoRepository iPedidoRepository) {
        this.iPedidoRepository = iPedidoRepository;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO) {
        LOGGER.info("Salvando pedido!");
        LOGGER.debug("Pedido: {}", pedidoDTO.getCodigo());

        Pedido pedido = new Pedido();

        pedido.setCodPedido(pedidoDTO.getCodigo());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setDataPedido(pedidoDTO.getDataPedido());
        pedido.setFuncionario(pedidoDTO.getFuncionario());
        pedido.setProduto(pedidoDTO.getProduto());
        pedido.setVendas(pedidoDTO.getVendas());

        this.validate(pedidoDTO);
        pedido = this.iPedidoRepository.save(pedido);

        return PedidoDTO.of(pedido);
    }

    private void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando produto");
        if (pedidoDTO == null) {
            throw new IllegalArgumentException("Pedido não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(pedidoDTO.getCodigo())) {
            throw new IllegalArgumentException("Código do pedido não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(pedidoDTO.getStatus())) {
            throw new IllegalArgumentException("Nome do produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getDataPedido()))) {
            throw new IllegalArgumentException("A data do pedido não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getFuncionario()))) {
            throw new IllegalArgumentException("Funcionário não deve ser nulo ou vazio!");
        }
        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getProduto()))) {
            throw new IllegalArgumentException("O produto não deve ser nulo ou vazio!");
        }
    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoExistenteOptional = this.iPedidoRepository.findById(id);

        if (pedidoExistenteOptional.isPresent()) {
            Pedido pedidoExistente = pedidoExistenteOptional.get();

            LOGGER.info("Atualizando pedido... id: [{}]", pedidoDTO.getCodigo());
            LOGGER.debug("Payaload: {}", pedidoDTO);
            LOGGER.debug("Pedido Existente: {}", pedidoExistente);

            pedidoExistente.setCodPedido(pedidoDTO.getCodigo().toUpperCase());
            pedidoExistente.setStatus(pedidoDTO.getStatus());
            pedidoExistente.setDataPedido(pedidoDTO.getDataPedido());
            pedidoExistente.setFuncionario(pedidoDTO.getFuncionario());
            pedidoExistente.setProduto(pedidoDTO.getProduto());
            pedidoExistente.setVendas(pedidoDTO.getVendas());

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