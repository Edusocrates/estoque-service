package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.mappers;

import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.entities.EstoqueEntity;

public class EstoqueMapper {

    public static EstoqueEntity toEntity(Estoque estoque) {
        return EstoqueEntity.builder()
                .id(estoque.getId())
                .sku(estoque.getSku())
                .quantidadeDisponivel(estoque.getQuantidadeDisponivel())
                .build();
    }

    public static Estoque toDomain(EstoqueEntity entity) {
        return Estoque.builder()
                .id(entity.getId())
                .sku(entity.getSku())
                .quantidadeDisponivel(entity.getQuantidadeDisponivel())
                .build();
    }
}
