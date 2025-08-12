package com.servicos.business.services;


import com.servicos.business.converters.CategoriaServicoConverter;
import com.servicos.business.dtos.in.CategoriaServicoResquestDTO;
import com.servicos.business.dtos.out.CategoriaServicoResponseDTO;
import com.servicos.infastructure.entity.CategoriaServico;
import com.servicos.infastructure.exception.ConflitException;
import com.servicos.infastructure.exception.ResourceNotFoundException;
import com.servicos.infastructure.repository.CategoriaServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicoService {

    private final CategoriaServicoRepository repository;
    private final CategoriaServicoConverter converter;

    @Transactional
    public CategoriaServicoResponseDTO criar(CategoriaServicoResquestDTO dto) {
        if (repository.existsByNome(dto.getNome())) {
            throw new ConflitException("Já existe uma categoria com este nome");
        }

        CategoriaServico categoria = converter.toEntityFromCreate(dto);
        return converter.toDTO(repository.save(categoria));
    }

    @Transactional(readOnly = true)
    public List<CategoriaServicoResponseDTO> listarTodasAtivas() {
        return repository.findByAtivoTrue().stream()
                .map(converter::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaServicoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(converter::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
    }

    @Transactional
    public void inativar(Long id) {
        CategoriaServico categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        categoria.setAtivo(false);
        repository.save(categoria);
    }

    @Transactional
    public CategoriaServicoResponseDTO atualizar(Long id, CategoriaServicoResquestDTO dto) {
        CategoriaServico categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!categoria.getNome().equals(dto.getNome())) {
            if (repository.existsByNome(dto.getNome())) {
                throw new ConflitException("Já existe uma categoria com este nome");
            }
            categoria.setNome(dto.getNome());
        }

        categoria.setDescricao(dto.getDescricao());
        return converter.toDTO(repository.save(categoria));
    }
}
