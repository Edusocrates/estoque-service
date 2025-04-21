package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstoqueAtualizadoEvent {
    private String sku;
    private int quantidadeDisponivel;
}