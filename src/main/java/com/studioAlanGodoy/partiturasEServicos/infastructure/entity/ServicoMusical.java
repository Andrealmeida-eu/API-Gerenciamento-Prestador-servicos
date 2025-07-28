package com.studioAlanGodoy.partiturasEServicos.infastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "servico")
public class ServicoMusical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal precoHora;
    private Integer horasEstimadas;


    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaServico categoria;


    // Getters e Setters
}
