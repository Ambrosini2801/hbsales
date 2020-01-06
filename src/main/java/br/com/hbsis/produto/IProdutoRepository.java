package br.com.hbsis.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


interface IProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT * FROM dbo.produto WHERE cod_produto =:codProduto",nativeQuery = true)
    Optional<Produto> findCodProduto(@Param("codProduto")String codProduto);

    @Query(value = "SELECT * FROM dbo.produto p\n" +
            "INNER JOIN dbo.linha l on l.id= p.fk_id_linha\n" +
            "INNER JOIN dbo.categoria c on c.id_categoria = l.id_categoria\n" +
            "INNER JOIN dbo.cad_fornecedor f on f.id=c.id_fornecedor\n" +
            "where f.id =:idFornecedor and nome_produto =:nomeProduto",nativeQuery = true)
    Optional<Produto>  findProdutoByFornecedor(@Param("idFornecedor")Long idFornecedor,@Param("nomeProduto")String nomeProduto);



}