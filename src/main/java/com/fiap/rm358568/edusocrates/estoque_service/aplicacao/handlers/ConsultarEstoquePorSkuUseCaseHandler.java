package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;

import com.fiap.rm358568.edusocrates.estoque_service.API.exceptions.EstoqueNotFoundException;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.ConsultarEstoquePorSkuUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsultarEstoquePorSkuUseCaseHandler implements ConsultarEstoquePorSkuUseCase {

    private EstoqueGateway estoqueGateway;


    @Override
    public Estoque consultarPorSku(String sku) {
        log.info("Consultando estoque para o SKU: {}", sku);

        Estoque estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(() -> new EstoqueNotFoundException("Estoque não encontrado para o SKU: " + sku));

        log.info("Estoque encontrado: {}", estoque);
        return estoque;
    }
}
