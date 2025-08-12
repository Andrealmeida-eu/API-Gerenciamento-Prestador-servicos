package com.servicos.business.dtos.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaServicoResquestDTO {
    private Long id;
    private String nome;
    private String descricao;
    private boolean ativo;
}

