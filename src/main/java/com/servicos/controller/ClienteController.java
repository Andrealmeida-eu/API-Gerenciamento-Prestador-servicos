package com.servicos.controller;

import com.servicos.business.dtos.in.ClienteResquestDTO;
import com.servicos.business.dtos.out.ClienteResponseDTO;
import com.servicos.business.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
 @RestController
@RequestMapping("/servicos-musicais/clientes")
public class ClienteController {


     private final ClienteService clienteService;

     @PostMapping
     public ResponseEntity<ClienteResponseDTO> criarCliente(@Valid @RequestBody ClienteResquestDTO dto) {
         ClienteResponseDTO clienteCriado = clienteService.criarCliente(dto);
         return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
     }


    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodosClientes() {

        List<ClienteResponseDTO> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }


    @PutMapping("/{id}/up")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody ClienteResquestDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(clienteService.criarCliente(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/parcial")
    public ResponseEntity<ClienteResponseDTO> atualizarClienteParcialmente(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(clienteService.atualizarParcialmente(id, updates));
    }
}
