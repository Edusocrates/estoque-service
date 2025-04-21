package com.fiap.rm358568.edusocrates.estoque_service.API.responses;


import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;

import java.util.UUID;

public record EstoqueResponse(
        UUID id,
        String sku,
        int quantidadeDisponivel
) {
    public static EstoqueResponse fromDomain(Estoque estoque) {
        return new EstoqueResponse(
                estoque.getId(),
                estoque.getSku(),
                estoque.getQuantidadeDisponivel()
        );
    }
}
