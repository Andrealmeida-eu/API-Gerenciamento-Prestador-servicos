package com.studioAlanGodoy.partiturasEServicos.controller;

import com.studioAlanGodoy.partiturasEServicos.business.dtos.in.CategoriaServicoResquestDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.in.ServicoMusicalResquestDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.out.CategoriaServicoResponseDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.out.ServicoMusicalResponseDTO;

import com.studioAlanGodoy.partiturasEServicos.business.services.CategoriaServicoService;
import com.studioAlanGodoy.partiturasEServicos.business.services.ServicoMusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController

@RequiredArgsConstructor
@RequestMapping("/servicos-musicais/servico")
public class ServicoMusicalController {

    private final ServicoMusicalService service;
    private final CategoriaServicoService catService;

    @GetMapping
    public ResponseEntity<List<ServicoMusicalResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }



    @GetMapping("/{id}")
    public ResponseEntity<ServicoMusicalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ServicoMusicalResponseDTO> criar(@RequestBody ServicoMusicalResquestDTO dto, Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoMusicalResponseDTO> atualizar(@PathVariable Long id, @RequestBody ServicoMusicalResquestDTO dto) {
        ServicoMusicalResponseDTO response = service.salvar(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/categoria/criar")
    public ResponseEntity<CategoriaServicoResponseDTO> criar(@RequestBody CategoriaServicoResquestDTO dto) {
        CategoriaServicoResponseDTO categoria = catService.criar(dto);
        return ResponseEntity.created(URI.create("/api/categorias-servico/" + dto.getId()))
                .body(categoria);
    }

    @GetMapping("/categoria/listarAtivos")
    public ResponseEntity<List<CategoriaServicoResponseDTO>> listarAtivas() {
        return ResponseEntity.ok(catService.listarTodasAtivas());
    }

    @GetMapping("/categoria/{id}/buscarId")
    public ResponseEntity<CategoriaServicoResponseDTO> buscarCatPorId(@PathVariable Long id) {
        return ResponseEntity.ok(catService.buscarPorId(id));
    }

    @PutMapping("categoria/{id}/atualizar")
    public ResponseEntity<CategoriaServicoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody CategoriaServicoResquestDTO dto) {
        return ResponseEntity.ok(catService.atualizar(id, dto));
    }

    @DeleteMapping("categoria/{id}/deletar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        catService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/categoria/{id}/parcial")
    public ResponseEntity<CategoriaServicoResponseDTO> atualizarParcialmente(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.atualizarParcialmente(id, updates));
    }

}
