package br.com.hbsis.Vendas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IVendasRepository extends JpaRepository<Vendas, Long> {

    Optional<Vendas> findById(Long id);

    Optional<Vendas> findVendasById(Long idVendas);
}