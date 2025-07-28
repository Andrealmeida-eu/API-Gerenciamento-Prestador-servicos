package com.studioAlanGodoy.partiturasEServicos.infastructure.repository;

import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.Cliente;
import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.Pedido;
import com.studioAlanGodoy.partiturasEServicos.infastructure.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);
  //  List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    // Método para contar pedidos com status diferente do especificado
    long countByStatusNot(StatusPedido status);
}
