package br.com.hbsis.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IPedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findById(Long id);

}