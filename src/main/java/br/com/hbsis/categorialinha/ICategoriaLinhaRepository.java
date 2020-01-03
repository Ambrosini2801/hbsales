package br.com.hbsis.categorialinha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface ICategoriaLinhaRepository extends JpaRepository<CategoriaLinha, Long> {

    @Query(value = "SELECT * FROM dbo.linha WHERE cod_linha =:codCategoriaLinha",nativeQuery = true)
    Optional<CategoriaLinha> findCategoriaLinhaByCod(@Param("codCategoriaLinha")String codCategoriaLinha);
}