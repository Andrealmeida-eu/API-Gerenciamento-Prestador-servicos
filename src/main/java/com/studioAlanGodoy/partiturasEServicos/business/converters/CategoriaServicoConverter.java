package com.studioAlanGodoy.partiturasEServicos.business.converters;

import com.studioAlanGodoy.partiturasEServicos.business.dtos.in.CategoriaServicoResquestDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.out.CategoriaServicoResponseDTO;
import com.studioAlanGodoy.partiturasEServicos.business.dtos.out.ClienteResponseDTO;
import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.CategoriaServico;
import com.studioAlanGodoy.partiturasEServicos.infastructure.entity.Cliente;
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
