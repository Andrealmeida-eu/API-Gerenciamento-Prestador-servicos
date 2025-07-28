package com.studioAlanGodoy.partiturasEServicos.business.dtos.in;

import com.studioAlanGodoy.partiturasEServicos.infastructure.enums.StatusPedido;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResquestDTO {

    private Long id;
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private StatusPedido status;
    private List<ItemPedidoResquestDTO> itens;

}
