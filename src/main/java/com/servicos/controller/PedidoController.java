package com.servicos.controller;


import com.servicos.business.dtos.in.PedidoResquestDTO;
import com.servicos.business.dtos.out.PedidoResponseDTO;
import com.servicos.business.services.PedidoService;
import com.servicos.infastructure.enums.StatusPedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequiredArgsConstructor
@RequestMapping("/servicos-musicais/Pedido")
public class PedidoController {
    private final PedidoService service;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoResquestDTO dto) {
        PedidoResponseDTO pedidoCriado = service.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(
            @PathVariable Long id, @RequestParam StatusPedido status) {
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }


    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodosPedidos() {
        List<PedidoResponseDTO> pedidos = service.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/pedido/{id}/parcial")
    public ResponseEntity<PedidoResponseDTO> atualizarPedidoParcialmente(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.atualizarParcialmente(id, updates));
    }


}
