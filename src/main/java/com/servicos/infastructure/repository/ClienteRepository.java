package com.servicos.infastructure.repository;

import com.servicos.infastructure.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);

    boolean existsByNome(String nome);
}
