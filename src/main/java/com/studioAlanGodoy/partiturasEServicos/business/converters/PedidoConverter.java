package com.studioAlanGodoy.partiturasEServicos.business.converters;

import com.studioAlanGodoy.partiturasEServicos.business.dtos.out.PedidoResponseDTO;
import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoConverter {

    private final ItemPedidoConverter itemPedidoConverter;

    public PedidoResponseDTO toDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setStatus(pedido.getStatus());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setDataEntrega(pedido.getDataEntrega());
        dto.setClienteNome(pedido.getCliente().getNome());
        dto.setItens(pedido.getItens().stream()
                .map(itemPedidoConverter::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }

 /*   public Pedido toEntity(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        // Mapear campos
        return pedido;
    }

  */
}
