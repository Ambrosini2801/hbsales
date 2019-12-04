package br.com.hbsis.produto;

import java.util.List;
import java.util.Optional;

public interface IProdutoRepository {
    Produto save(Produto produto);

    Optional<Produto> findById(Long id);

    void deleteById(Long id);

    List<Produto> findAll();
}
