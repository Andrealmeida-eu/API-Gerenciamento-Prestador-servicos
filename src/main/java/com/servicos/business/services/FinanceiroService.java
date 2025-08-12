package com.servicos.business.services;

import com.servicos.business.converters.CategoriaServicoConverter;
import com.servicos.business.dtos.out.CategoriaServicoResponseDTO;
import com.servicos.infastructure.entity.Pedido;
import com.servicos.infastructure.enums.StatusPedido;
import com.servicos.infastructure.exception.DataInvalidaException;
import com.servicos.infastructure.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceiroService {
    private final PedidoRepository pedidoRepository;
    private final CategoriaServicoConverter converter;


    /**
     * Calcula a receita total de todos os pedidos não cancelados
     *
     * @return Valor total da receita
     */
    public BigDecimal calcularReceitaTotal() {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getStatus() != StatusPedido.CANCELADO)
                .map(this::calcularTotalPedido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula a receita em um período específico
     *
     * @param inicio Data de início do período
     * @param fim    Data final do período
     * @return Valor total da receita no período
     * @throws DataInvalidaException Se as datas forem inválidas
     */
    public BigDecimal calcularReceitaPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        validarDatas(inicio, fim);

        return pedidoRepository.findByDataPedidoBetween(inicio, fim).stream()
                .filter(p -> p.getStatus() != StatusPedido.CANCELADO)
                .map(this::calcularTotalPedido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula a receita agrupada por tipo de serviço
     *
     * @return Mapa com tipo de serviço como chave e valor total como valor
     */
    public Map<CategoriaServicoResponseDTO, BigDecimal> calcularReceitaPorTipoServico() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream()
                .filter(p -> p.getStatus() != StatusPedido.CANCELADO)
                .flatMap(pedido -> pedido.getItens().stream())
                .collect(Collectors.groupingBy(
                        item -> converter.toDTO(item.getServico().getCategoria()),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())),
                                BigDecimal::add
                        )
                ));
    }

    /**
     * Valida se as datas para consulta são válidas
     *
     * @param inicio Data de início
     * @param fim    Data final
     * @throws DataInvalidaException Se alguma validação falhar
     */
    private void validarDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null) {
            throw new DataInvalidaException("Data de início não pode ser nula");
        }

        if (fim == null) {
            throw new DataInvalidaException("Data final não pode ser nula");
        }

        if (fim.isBefore(inicio)) {
            throw new DataInvalidaException("Data final deve ser posterior à data de início");
        }

        LocalDateTime agora = LocalDateTime.now();
        if (inicio.isAfter(agora)) {
            throw new DataInvalidaException("Data de início não pode ser no futuro");
        }

        if (fim.isAfter(agora)) {
            throw new DataInvalidaException("Data final não pode ser no futuro");
        }
    }

    /**
     * Calcula o valor total de um pedido individual
     *
     * @param pedido Pedido a ser calculado
     * @return Valor total do pedido
     */
    private BigDecimal calcularTotalPedido(Pedido pedido) {
        return pedido.getItens().stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula a média de valor dos pedidos
     *
     * @return Média de valor dos pedidos não cancelados
     */
    public BigDecimal calcularMediaPedidos() {
        long totalPedidos = pedidoRepository.countByStatusNot(StatusPedido.CANCELADO);

        if (totalPedidos == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal receitaTotal = calcularReceitaTotal();
        return receitaTotal.divide(BigDecimal.valueOf(totalPedidos), 2, BigDecimal.ROUND_HALF_UP);
    }

}
