package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;

import com.fiap.rm358568.edusocrates.estoque_service.API.exceptions.EstoqueNotFoundException;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.DebitarEstoqueUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DebitarEstoqueUseCaseHandler implements DebitarEstoqueUseCase {

    private EstoqueGateway estoqueGateway;


    @Override
    public Estoque debitarEstoque(String sku, int quantidade) {
        log.info("Debitando estoque para SKU: {} com quantidade: {}", sku, quantidade);

        Estoque estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(() -> new EstoqueNotFoundException("Estoque não encontrado para SKU: " + sku));

        estoque.remover(quantidade);
        Estoque estoqueAtualizado = estoqueGateway.salvar(estoque);
        log.info("Estoque atualizado: {}", estoqueAtualizado);
        return estoqueAtualizado;
    }
}
