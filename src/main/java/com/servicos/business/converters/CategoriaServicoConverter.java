package com.servicos.business.converters;

import com.servicos.business.dtos.in.CategoriaServicoResquestDTO;
import com.servicos.business.dtos.out.CategoriaServicoResponseDTO;
import com.servicos.infastructure.entity.CategoriaServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CategoriaServicoConverter {


    public CategoriaServicoResponseDTO toDTO(CategoriaServico categoriaServico) {
        CategoriaServicoResponseDTO dto = new CategoriaServicoResponseDTO();
        dto.setNome(categoriaServico.getNome());
        dto.setDescricao(categoriaServico.getDescricao());
        dto.setAtivo(categoriaServico.isAtivo());
        return dto;
    }

    public CategoriaServico toEntity(CategoriaServicoResponseDTO dto) {
        CategoriaServico categoria = new CategoriaServico();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setAtivo(dto.isAtivo());
        return categoria;
    }

    public CategoriaServico toEntityFromCreate(CategoriaServicoResquestDTO dto) {
        CategoriaServico categoria = new CategoriaServico();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoria;
    }

}
