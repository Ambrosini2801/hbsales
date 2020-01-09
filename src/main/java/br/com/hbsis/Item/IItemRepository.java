package br.com.hbsis.Item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long id);

    Item save(Item itemExistente);

    void deleteById(Long id);
}