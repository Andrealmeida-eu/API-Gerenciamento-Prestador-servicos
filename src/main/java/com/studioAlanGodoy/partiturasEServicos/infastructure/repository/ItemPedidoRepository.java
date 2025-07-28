package com.studioAlanGodoy.partiturasEServicos.infastructure.repository;

import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.Cliente;
import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
