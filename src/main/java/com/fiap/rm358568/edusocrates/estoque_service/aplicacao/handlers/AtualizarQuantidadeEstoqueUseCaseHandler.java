package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;


import com.fiap.rm358568.edusocrates.estoque_service.API.exceptions.EstoqueNotFoundException;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.AtualizarQuantidadeEstoqueUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AtualizarQuantidadeEstoqueUseCaseHandler implements AtualizarQuantidadeEstoqueUseCase {


    private final EstoqueGateway estoqueGateway;

    @Override
    @Transactional
    public Estoque atualizarQuantidadeEstoque(String sku, int quantidade) {
        log.info("Atualizando estoque para SKU: {} com quantidade: {}", sku, quantidade);
        Estoque estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(() -> new EstoqueNotFoundException("Estoque não encontrado para SKU: " + sku));

        if (quantidade > 0) {
            estoque.adicionar(quantidade);
        } else {
            estoque.remover(-quantidade);
        }

        Estoque estoqueAtualizado = estoqueGateway.salvar(estoque);

        log.info("Estoque atualizado: {}", estoqueAtualizado);
        return estoqueAtualizado;
    }
}
