package com.servicos.business.dtos.in;

import com.servicos.infastructure.entity.Pedido;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPedidoResquestDTO {

    private Long id;
    private Long pedidoId;
    private Long servicoId;
    private Pedido pedido;
    private String servicoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    // Getters e Setters
}
