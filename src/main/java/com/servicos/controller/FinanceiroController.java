package com.servicos.controller;

import com.servicos.business.dtos.out.CategoriaServicoResponseDTO;
import com.servicos.business.services.FinanceiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

@RequiredArgsConstructor
@RequestMapping("/servicos-musicais/receita")
public class FinanceiroController {

    private final FinanceiroService service;

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> receitaTotal() {
        return ResponseEntity.ok(service.calcularReceitaTotal());
    }

    @GetMapping("/periodo")
    public ResponseEntity<BigDecimal> receitaPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(service.calcularReceitaPeriodo(inicio, fim));
    }


    @GetMapping("/receita-por-servico")
    public ResponseEntity<?> receitaPorTipoServico() {
        try {
            Map<CategoriaServicoResponseDTO, BigDecimal> resultado = service.calcularReceitaPorTipoServico();

            if (resultado.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Transforma o Map em uma lista de objetos para melhor resposta JSON
            List<Map<String, Object>> response = resultado.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> item = new LinkedHashMap<>();
                        item.put("categoria", entry.getKey());
                        item.put("receitaTotal", entry.getValue());
                        return item;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao calcular receita por serviço: " + e.getMessage());
        }
    }


}
