package com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways;

import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;

import java.util.Optional;

public interface EstoqueGateway {

    Estoque salvar(Estoque estoque);

    Optional<Estoque> buscarPorSku(String sku);

    void deletarPorSku(String sku);
}
