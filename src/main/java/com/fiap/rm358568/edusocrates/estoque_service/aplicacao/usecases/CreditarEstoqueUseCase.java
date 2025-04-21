package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases;

import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;

public interface CreditarEstoqueUseCase {

    Estoque executar(String sku, int quantidade);
}
