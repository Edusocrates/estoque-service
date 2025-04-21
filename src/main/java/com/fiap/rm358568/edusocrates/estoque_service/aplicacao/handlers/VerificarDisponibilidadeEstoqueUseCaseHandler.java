package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;

import com.fiap.rm358568.edusocrates.estoque_service.API.exceptions.EstoqueNotFoundException;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.VerificarDisponibilidadeEstoqueUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VerificarDisponibilidadeEstoqueUseCaseHandler implements VerificarDisponibilidadeEstoqueUseCase {


    private EstoqueGateway estoqueGateway;


    @Override
    public boolean verificarDisponibilidade(String sku, int quantidade) {
        log.info("Verificando disponibilidade de estoque para SKU: {} e quantidade: {}", sku, quantidade);
        var estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(() -> new EstoqueNotFoundException("SKU não encontrado!"));

        boolean disponivel = estoque.getQuantidadeDisponivel() >= quantidade;
        log.info("Disponibilidade de estoque para SKU {}: {}", sku, disponivel);
        return disponivel;
    }
}
