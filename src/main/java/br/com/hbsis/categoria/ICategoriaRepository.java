package br.com.hbsis.categoria;

import br.com.hbsis.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public
interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findById(String id);

    @Query(value = "SELECT * FROM dbo.categoria where cod_categoria =:codCategoria ",nativeQuery = true)
    Optional<Categoria> findByCod(@Param("codCategoria")String codCategoria);


    @Query(value = "SELECT * FROM dbo.categoria where nome_categoria=:nomeCategoria and id_fornecedor =:idFornecedor",nativeQuery = true)
    Optional<Categoria> findByFornecedor(@Param("nomeCategoria")String nomeCategoria,@Param("idFornecedor")Long idFornecedor);
}



