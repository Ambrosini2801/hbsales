package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaLinhaRepository {

  public  static void findCategoriaById(long parseLong) {
  }

    CategoriaLinha save(CategoriaLinha categorialinha);

    Optional<CategoriaLinha> findById(Long id);

    void deleteById(Long id);

    List<Categoria> findAll();
    }
