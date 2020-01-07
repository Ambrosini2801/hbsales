package br.com.hbsis.Funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findById(Long id);

}
