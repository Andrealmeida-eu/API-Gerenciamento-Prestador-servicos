package com.studioAlanGodoy.partiturasEServicos.business.services;

import com.studioAlanGodoy.partiturasEServicos.business.converters.PedidoConverter;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.in.ItemPedidoResquestDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.in.PedidoResquestDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.out.ItemPedidoResponseDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.out.PedidoResponseDTO;
import com.studioAlanGodoy.partiturasEServicos.infastructure.EntityUpdate;
import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.ItemPedido;
import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.Pedido;
import com.studioAlanGodoy.partiturasEServicos.infastructure.enums.StatusPedido;
import com.studioAlanGodoy.partiturasEServicos.infastructure.exception.ConflitException;
import com.studioAlanGodoy.partiturasEServicos.infastructure.exception.ResourceNotFoundException;
import com.studioAlanGodoy.partiturasEServicos.infastructure.repository.ClienteRepository;
import com.studioAlanGodoy.partiturasEServicos.infastructure.repository.PedidoRepository;
import com.studioAlanGodoy.partiturasEServicos.infastructure.repository.ServicoMusicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {


    private final PedidoConverter converter;
    private final ClienteRepository clienteRepository;
    private final ServicoMusicalRepository servicoRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoResponseDTO criarPedido(PedidoResquestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setCliente(clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado")));
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGENDADO);
        pedido.setDataEntrega(dto.getDataEntrega());

        for (ItemPedidoResquestDTO itemDto : dto.getItens()) {
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);

            if (itemDto.getServicoId() != null) {
                item.setServico(servicoRepository.findById(itemDto.getServicoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado")));
                item.setPrecoUnitario(item.getServico().getPrecoHora()
                        .multiply(BigDecimal.valueOf(item.getServico().getHorasEstimadas())));
            }

            item.setQuantidade(itemDto.getQuantidade());
            pedido.getItens().add(item);
        }

        return converter.toDTO(pedidoRepository.save(pedido));
    }

    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        pedido.setStatus(status);
        return converter.toDTO(pedidoRepository.save(pedido));
    }

    public List<PedidoResponseDTO> listarTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public PedidoResponseDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrada"));

        try {
            EntityUpdate.updatePartialEntity(pedido, updates);
            return converter.toDTO(pedidoRepository.save(pedido));
        } catch (IllegalAccessException e) {
            throw new ConflitException("Erro ao atualizar categoria: " + e.getMessage());
        }
    }



}
