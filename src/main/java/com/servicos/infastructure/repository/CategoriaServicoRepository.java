package com.servicos.infastructure.repository;

import com.servicos.infastructure.entity.CategoriaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaServicoRepository extends JpaRepository<CategoriaServico, Long> {
    Optional<CategoriaServico> findByNome(String nome);
    List<CategoriaServico> findByAtivoTrue();
    boolean existsByNome(String nome);
}