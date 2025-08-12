package com.servicos.business.converters;

import com.servicos.business.dtos.out.ItemPedidoResponseDTO;
import com.servicos.infastructure.entity.ItemPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemPedidoConverter {

    public ItemPedidoResponseDTO toDTO(ItemPedido item) {
        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();
        dto.setServicoNome(item.getServico().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        return dto;
    }

   /* public ItemPedido toEntity(ItemPedidoDTO dto) {
        ItemPedido item = new ItemPedido();
        item.setId(dto.getId());
        item.setPedido(dto.getPedido());
        item.setServicoId(dto.getServico().getId());
        item.setServicoNome(dto.getServico().getNome());
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        return item;
    }

    */
}
