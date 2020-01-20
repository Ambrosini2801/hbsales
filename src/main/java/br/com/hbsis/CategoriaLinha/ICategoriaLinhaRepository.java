package br.com.hbsis.CategoriaLinha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface ICategoriaLinhaRepository extends JpaRepository<CategoriaLinha, Long> {

    @Query(value = "SELECT * FROM dbo.linha WHERE cod_linha =:codCategoriaLinha",nativeQuery = true)
    Optional<CategoriaLinha> findCategoriaLinhaByCod(@Param("codCategoriaLinha")String codCategoriaLinha);


    @Query(value = "SELECT * FROM dbo.linha l \n" +
            "INNER JOIN dbo.categoria c on c.id_categoria = l.id_categoria\n" +
            "INNER JOIN dbo.cad_fornecedor f on f.id=c.id_fornecedor\n" +
            "where f.id =:idFornecedor and l.nome_linha =:nomeLinha",nativeQuery = true)
    Optional<CategoriaLinha>findByFornecedor(@Param("idFornecedor")Long idFornecedor,@Param("nomeLinha")String nomeLinha);

    Optional<CategoriaLinha> findById(boolean present);
}