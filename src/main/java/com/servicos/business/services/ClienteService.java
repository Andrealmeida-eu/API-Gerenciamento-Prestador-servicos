package com.servicos.business.services;

import com.servicos.business.converters.ClienteConverter;
import com.servicos.business.dtos.in.ClienteResquestDTO;
import com.servicos.business.dtos.out.ClienteResponseDTO;
import com.servicos.infastructure.EntityUpdate;
import com.servicos.infastructure.entity.Cliente;
import com.servicos.infastructure.exception.ConflitException;
import com.servicos.infastructure.exception.ResourceNotFoundException;
import com.servicos.infastructure.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
    @RequiredArgsConstructor
    public class ClienteService {

        private final ClienteRepository clienteRepository;
        private final ClienteConverter converter;
    /**
     * Cria um novo cliente no sistema
     * @param dto DTO contendo os dados do cliente
     * @return ClienteDTO com os dados do cliente criado
     * @throws ConflitException Se já existir cliente com mesmo email ou telefone
     */
    @Transactional
    public ClienteResponseDTO criarCliente(ClienteResquestDTO dto) {
        validarCliente(dto); // Valida os dados do cliente

        Cliente cliente = converter.toEntity(dto);
        cliente = clienteRepository.save(cliente); // Persiste o cliente

        return converter.toDTO(cliente); // Retorna o DTO do cliente criado
    }

    /**
     * Valida os dados do cliente antes de persistir
     * @param dto DTO com os dados do cliente
     * @throws ConflitException Se as validações falharem
     */
    private void validarCliente(ClienteResquestDTO dto) {
        if (dto.getEmail() != null && clienteRepository.existsByEmail(dto.getEmail())) {
            throw new ConflitException("Já existe um cliente com este email");
        }

        if (dto.getTelefone() != null && clienteRepository.existsByTelefone(dto.getTelefone())) {
            throw new ConflitException("Já existe um cliente com este telefone");
        }
    }

    /**
     * Lista todos os clientes cadastrados
     * @return Lista de ClienteDTO
     */
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente por ID
     * @param id ID do cliente
     * @return ClienteDTO encontrado
     * @throws ResourceNotFoundException Se o cliente não for encontrado
     */
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        return converter.toDTO(cliente);
    }

    /**
     * Atualiza os dados de um cliente existente
     * @param id ID do cliente a ser atualizado
     * @param dto DTO com os novos dados
     * @return ClienteDTO atualizado
     * @throws ResourceNotFoundException Se o cliente não for encontrado
     * @throws ConflitException Se as validações de email/telefone falharem
     */
    @Transactional
    public ClienteResponseDTO atualizarCliente(Long id, ClienteResponseDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));

        // Verifica se o email foi alterado e se já existe
        if (dto.getEmail() != null && !dto.getEmail().equals(clienteExistente.getEmail())) {
            if (clienteRepository.existsByEmail(dto.getEmail())) {
                throw new ConflitException("Já existe um cliente com este email");
            }
            clienteExistente.setEmail(dto.getEmail());
        }

        // Verifica se o telefone foi alterado e se já existe
        if (dto.getTelefone() != null && !dto.getTelefone().equals(clienteExistente.getTelefone())) {
            if (clienteRepository.existsByTelefone(dto.getTelefone())) {
                throw new ConflitException("Já existe um cliente com este telefone");
            }
            clienteExistente.setTelefone(dto.getTelefone());
        }

        // Atualiza outros campos
        clienteExistente.setNome(dto.getNome());

        clienteRepository.save(clienteExistente);
        return converter.toDTO(clienteExistente);
    }

    /**
     * Remove um cliente do sistema
     * @param id ID do cliente a ser removido
     * @throws ResourceNotFoundException Se o cliente não for encontrado
     */
    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    @Transactional
    public ClienteResponseDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrada"));

        // Validação especial para nome único
        if (updates.containsKey("nome")) {
            String novoNome = (String) updates.get("nome");
            if (!cliente.getNome().equals(novoNome)) {
                if (clienteRepository.existsByNome(novoNome)) {
                    throw new ConflitException("Já existe uma categoria com este nome");
                }
            }
        }

        try {
            EntityUpdate.updatePartialEntity(cliente, updates);
            return converter.toDTO(clienteRepository.save(cliente));
        } catch (IllegalAccessException e) {
            throw new ConflitException("Erro ao atualizar categoria: " + e.getMessage());
        }
    }
}



