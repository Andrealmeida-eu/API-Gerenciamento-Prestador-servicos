package com.servicos.business.dtos.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaServicoResponseDTO {

    private String nome;
    private String descricao;
    private boolean ativo;


}

