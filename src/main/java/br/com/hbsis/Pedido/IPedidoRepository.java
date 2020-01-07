package br.com.hbsis.Pedido;

import java.util.Optional;

public interface IPedidoRepository {
    Optional<Pedido> findById(Long id);

    Pedido save(Pedido pedidoExistente);

    void deleteById(Long id);
}

