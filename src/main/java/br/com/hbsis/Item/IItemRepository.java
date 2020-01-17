package br.com.hbsis.Item;

import br.com.hbsis.Pedido.Pedido;
import br.com.hbsis.Produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByPedido(Pedido pedido);


    Optional<Item> findById(Long id);

    Item save(Item itemExistente);

    void deleteById(Long id);

    @Query(value = "select * from dbo.itens where fk_pedido=:idPedido", nativeQuery = true)
    List<Item> findItensByPedido(@Param("idPedido") Long idPedido);

}