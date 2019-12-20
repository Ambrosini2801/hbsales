package br.com.hbsis.categorialinha;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ICategoriaLinhaRepository extends JpaRepository<CategoriaLinha, Long> {
    CategoriaLinha findByCodLinha(String codLinha);

    Optional<CategoriaLinha> findById(Long id);

}
