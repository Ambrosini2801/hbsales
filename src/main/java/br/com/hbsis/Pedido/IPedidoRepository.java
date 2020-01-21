package br.com.hbsis.Pedido;

import br.com.hbsis.Funcionario.FuncionarioDTO;
import br.com.hbsis.Vendas.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findById(Long id);

    List<Pedido> findByVendas(Vendas vendas);

    List<Pedido> findById(FuncionarioDTO funcionario);

    Pedido findByid(Long id);

    Pedido findPedidoByid(Long id);
}