package com.fiap.rm358568.edusocrates.estoque_service.API.mappers;


import com.fiap.rm358568.edusocrates.estoque_service.API.requests.AtualizarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.API.responses.EstoqueResponse;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;

public class EstoqueMapper {

    public static Estoque toDomain(CriarEstoqueRequest request) {
        return Estoque.builder()
                .sku(request.sku())
                .quantidadeDisponivel(request.quantidadeDisponivel())
                .build();
    }

    public static Estoque toDomain(AtualizarEstoqueRequest request) {
        return Estoque.builder()
                .sku(request.sku())
                .quantidadeDisponivel(request.novaQuantidade())
                .build();
    }

    public static EstoqueResponse toResponse(Estoque estoque) {
        return EstoqueResponse.fromDomain(estoque);
    }
}