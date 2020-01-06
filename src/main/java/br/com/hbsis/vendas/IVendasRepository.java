package br.com.hbsis.vendas;

import br.com.hbsis.Fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IVendasRepository extends JpaRepository<Vendas, Long> {
    Vendas save(Vendas vendas);

    Optional<Vendas> findById(Long id);



    void deleteById(Long id);

}
