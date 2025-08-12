package com.servicos.business.dtos.out;

import com.servicos.infastructure.entity.Pedido;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPedidoResponseDTO {


    private Pedido pedido;
    private String servicoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    // Getters e Setters
}
