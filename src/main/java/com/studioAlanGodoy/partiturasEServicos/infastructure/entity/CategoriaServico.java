package com.studioAlanGodoy.partiturasEServicos.infastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "categorias_servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(length = 500)
    private String descricao;

    private boolean ativo = true;
}

