package br.com.hbsis.vendas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface IVendasRepository extends JpaRepository<Vendas, Long> {

    Optional<Vendas> findById(Long id);

    Optional<Vendas> findVendasById(Long idVendas);
}
