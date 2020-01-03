package br.com.hbsis.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public
interface IFornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Optional<Fornecedor> findById(String id);


}
