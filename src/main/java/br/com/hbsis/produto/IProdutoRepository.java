package br.com.hbsis.produto;

import org.springframework.data.jpa.repository.JpaRepository;

interface IProdutoRepository extends JpaRepository<Produto, Long> {

}

