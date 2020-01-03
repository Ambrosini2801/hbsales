package br.com.hbsis.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


interface IProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findAll();




}