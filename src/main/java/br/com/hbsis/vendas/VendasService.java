package br.com.hbsis.vendas;


import br.com.hbsis.Fornecedor.FornecedorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class VendasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendasService.class);
    private final IVendasRepository iVendasRepository;
    private final FornecedorService fornecedorService;

    @Autowired
    public VendasService(IVendasRepository iVendasRepository, FornecedorService fornecedorService) {
        this.iVendasRepository = iVendasRepository;
        this.fornecedorService = fornecedorService;
    }

    public VendasDTO save(VendasDTO vendasDTO) {

        LOGGER.info("Salvando Período de Vendas!");
        LOGGER.debug("Vendas: {}", vendasDTO);

        Vendas vendas = new Vendas();
        vendas.setInicioVendas(vendasDTO.getInicioVendas());
        vendas.setFimVendas(vendasDTO.getFimVendas());
        vendas.setRetiradaPedido(vendasDTO.getRetiradaPedido());
        vendas.setDescricao(vendasDTO.getDescricao());
        vendas.setFornecedor(fornecedorService.findFornecedorById1(vendasDTO.getFornecedor()));
        this.validate(vendasDTO);
        vendas = this.iVendasRepository.save(vendas);
        return VendasDTO.of(vendas);
    }

    private void validate(VendasDTO vendasDTO) {
        LOGGER.info("Validando Vendas!");
        if (vendasDTO == null) {
            throw new IllegalArgumentException("Vendas não pode ser nula ou vazia!");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getInicioVendas()))) {
            throw new IllegalArgumentException("Início de vendas não pode ser nulo ou vazio!");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getFimVendas()))) {
            throw new IllegalArgumentException("Fim de vendas não pode ser nulo ou vazio!");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getRetiradaPedido()))) {
            throw new IllegalArgumentException("Retirada do pedido não deve ser nulo ou vazio!");
        }
        if (StringUtils.isEmpty(vendasDTO.getDescricao())) {
            throw new IllegalArgumentException("Descrição de vendas não deve ser nula ou vazia!");
        }
        if (StringUtils.isEmpty(vendasDTO.getDescricao())) {
            throw new IllegalArgumentException("Descrição de vendas deve conter no máximo 50 caracteres!");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getId()))) {
            throw new IllegalArgumentException("Id do fornecedor não deve ser nulo ou vazio!");
        }
        if (vendasDTO.getInicioVendas().isBefore(LocalDate.now())
                || vendasDTO.getFimVendas().isBefore(LocalDate.now()) || vendasDTO.getFimVendas().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Início das vendas não podem ser anteriores ao dia de hoje!");
        }
        if (vendasDTO.getFimVendas().isBefore(vendasDTO.getInicioVendas())) {
            throw new IllegalArgumentException("Fim das vendas não podem ser anteriores a data do início das vendas!");
        }
    }

    public VendasDTO update(VendasDTO vendasDTO, Long id) {
        Optional<Vendas> vendaExistenteOptional =
                this.iVendasRepository.findById(id);

        if (vendaExistenteOptional.isPresent()) {
            Vendas vendaExistente = vendaExistenteOptional.get();

            LOGGER.info("Atualizando vendas... id: [{}]", vendasDTO);
            LOGGER.debug("Payload: {}", vendasDTO);
            LOGGER.debug("Venda Existente: {}", vendaExistente);

            vendaExistente.setInicioVendas(vendasDTO.getInicioVendas());
            vendaExistente.setFimVendas(vendasDTO.getFimVendas());
            vendaExistente.setRetiradaPedido(vendasDTO.getRetiradaPedido());
            vendaExistente.setDescricao(vendasDTO.getDescricao());
            this.fornecedorService.findFornecedorById(vendasDTO.getId());

            vendaExistente = this.iVendasRepository.save(vendaExistente);
            return vendasDTO.of(vendaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public VendasDTO findByid(Long id) {
        Optional<Vendas> vendasOptional = this.iVendasRepository.findById(id);

        if (vendasOptional.isPresent()) {
            return VendasDTO.of(vendasOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe!", id));
    }

    public VendasDTO findById(Long id) {
        Optional<Vendas> periodoOptional = this.iVendasRepository.findById(id);

        if (periodoOptional.isPresent()) {
            return VendasDTO.of(periodoOptional.get());
        }
        throw new IllegalArgumentException(String.format("Período de vendas de ID {} não encontrado!", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para vendas de ID: [{}]", id);
        this.iVendasRepository.deleteById(id);
    }

    public Vendas findVendasById(Long idVendas) {
        Optional<Vendas> vendasOptional = iVendasRepository.findVendasById(idVendas);
        if (vendasOptional.isPresent()) {
            Vendas vendas = vendasOptional.get();
            return vendas;
        } else {
            LOGGER.info("ID de vendas não encontrado. findVendasById");
            return null;
        }
    }
}