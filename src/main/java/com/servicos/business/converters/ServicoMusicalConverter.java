package com.servicos.business.converters;

import com.servicos.business.dtos.in.ServicoMusicalResquestDTO;
import com.servicos.business.dtos.out.ServicoMusicalResponseDTO;
import com.servicos.infastructure.entity.ServicoMusical;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServicoMusicalConverter {

    public ServicoMusicalResponseDTO toDTO(ServicoMusical servico) {
        ServicoMusicalResponseDTO dto = new ServicoMusicalResponseDTO();
       dto.setNome(servico.getNome());
       dto.setDescricao(servico.getDescricao());
       dto.setPrecoHora(servico.getPrecoHora());
       dto.setHorasEstimadas(servico.getHorasEstimadas());
        return dto;
    }

   public ServicoMusical toEntity(ServicoMusicalResquestDTO dto) {
        ServicoMusical servico = new ServicoMusical();
       servico.setNome(dto.getNome());
       servico.setDescricao(dto.getDescricao());
       servico.setPrecoHora(dto.getPrecoHora());
       servico.setHorasEstimadas(dto.getHorasEstimadas());
        return servico;
    }

}
