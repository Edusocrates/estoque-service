package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;

import com.fiap.rm358568.edusocrates.estoque_service.API.exceptions.EstoqueNotFoundException;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.CreditarEstoqueUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.producers.EstoqueProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreditarEstoqueUseCaseHandler implements CreditarEstoqueUseCase {


    private final EstoqueGateway estoqueGateway;

    private final EstoqueProducer estoqueProducer;


    @Override
    @Transactional
    public Estoque executar(String sku, int quantidade) {
        log.info("Adicionando {} unidades ao estoque do SKU: {}", quantidade, sku);

        Estoque estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(() -> new EstoqueNotFoundException("Estoque não encontrado para SKU: " + sku));

        estoque.adicionar(quantidade);
        Estoque estoqueAtualizado = estoqueGateway.salvar(estoque);
        //estoqueProducer.enviarAtualizacaoEstoque(estoqueAtualizado);
        log.info("Estoque atualizado: {}", estoqueAtualizado);
        return estoqueAtualizado;
    }
}
