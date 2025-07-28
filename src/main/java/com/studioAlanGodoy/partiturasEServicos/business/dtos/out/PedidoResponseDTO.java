package com.studioAlanGodoy.partiturasEServicos.business.dtos.out;

import com.studioAlanGodoy.partiturasEServicos.infastructure.enums.StatusPedido;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponseDTO {


    private String clienteNome;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private StatusPedido status;
    private List<ItemPedidoResponseDTO> itens;

}
