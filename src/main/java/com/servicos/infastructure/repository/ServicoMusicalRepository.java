package com.servicos.infastructure.repository;

import com.servicos.infastructure.entity.ServicoMusical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoMusicalRepository extends JpaRepository<ServicoMusical, Long> {

    List<ServicoMusical> findByNomeContaining(String nome);
 //   List<ServicoMusical> findByTipo(TipoServico tipo);

}
