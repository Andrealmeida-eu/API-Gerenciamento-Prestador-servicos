package com.servicos.business.services;

import com.servicos.business.converters.CategoriaServicoConverter;
import com.servicos.business.converters.ServicoMusicalConverter;
import com.servicos.business.dtos.in.ServicoMusicalResquestDTO;
import com.servicos.business.dtos.out.CategoriaServicoResponseDTO;
import com.servicos.business.dtos.out.ServicoMusicalResponseDTO;
import com.servicos.infastructure.EntityUpdate;
import com.servicos.infastructure.entity.CategoriaServico;
import com.servicos.infastructure.entity.ServicoMusical;
import com.servicos.infastructure.exception.ConflitException;
import com.servicos.infastructure.exception.ResourceNotFoundException;
import com.servicos.infastructure.repository.CategoriaServicoRepository;
import com.servicos.infastructure.repository.ServicoMusicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoMusicalService {

    private final ServicoMusicalRepository repository;
    private final ServicoMusicalConverter converter;
    private final CategoriaServicoRepository categoriaServicoRepository;
    private final CategoriaServicoConverter categoriaServicoConverter;


    public List<ServicoMusicalResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public ServicoMusicalResponseDTO buscarPorId(Long id) {
        return converter.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrada")));
    }

    public ServicoMusicalResponseDTO salvar(ServicoMusicalResquestDTO dto) {

        // 1. Validação da categoria
        if (dto.getCategoriaId() == null) {
            throw new ConflitException("ID da categoria é obrigatório");
        }

        // 2. Verifica se a categoria existe
        CategoriaServico categoria = categoriaServicoRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + dto.getCategoriaId()));
        // 3. Converte DTO para entidade
        ServicoMusical entidade = converter.toEntity(dto);

        // 4. Associa a categoria encontrada
        entidade.setCategoria(categoria);

        // 5. Salva no banco
        ServicoMusical salvo = repository.save(entidade);

        return converter.toDTO(salvo);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public CategoriaServicoResponseDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        CategoriaServico categoria = categoriaServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        // Validação especial para nome único
        if (updates.containsKey("nome")) {
            String novoNome = (String) updates.get("nome");
            if (!categoria.getNome().equals(novoNome)) {
                if (categoriaServicoRepository.existsByNome(novoNome)) {
                    throw new ConflitException("Já existe uma categoria com este nome");
                }
            }
        }

        try {
            EntityUpdate.updatePartialEntity(categoria, updates);
            return categoriaServicoConverter.toDTO(categoriaServicoRepository.save(categoria));
        } catch (IllegalAccessException e) {
            throw new ConflitException("Erro ao atualizar categoria: " + e.getMessage());
        }
    }
}
