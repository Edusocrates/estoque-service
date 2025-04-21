package com.fiap.rm358568.edusocrates.estoque_service.dominio.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estoque {
    private UUID id;
    private String sku;
    private int quantidadeDisponivel;

    public void adicionar(int quantidade) {
        this.quantidadeDisponivel += quantidade;
    }

    public void remover(int quantidade) {
        if (quantidade > this.quantidadeDisponivel) {
            throw new IllegalArgumentException("Estoque insuficiente para SKU: " + sku);
        }
        this.quantidadeDisponivel -= quantidade;
    }
}
