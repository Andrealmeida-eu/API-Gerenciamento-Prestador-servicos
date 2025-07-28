package com.studioAlanGodoy.partiturasEServicos.infastructure.entity;

import com.studioAlanGodoy.partiturasEServicos.infastructure.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ItemPedido> itens = new ArrayList<>();
    // Ou nullable = true
    private LocalDateTime dataEntrega;
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.ORDINAL)
    private StatusPedido status;

    // Validação para garantir que a data de entrega seja futura
    @PrePersist
    @PreUpdate
    private void validateDataEntrega() {
        if (dataEntrega != null && dataEntrega.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data de entrega deve ser no futuro");
        }
    }

    // Getters e Setters
}
