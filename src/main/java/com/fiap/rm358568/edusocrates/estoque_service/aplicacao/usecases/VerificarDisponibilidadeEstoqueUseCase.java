package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases;

public interface VerificarDisponibilidadeEstoqueUseCase {

    boolean verificarDisponibilidade(String sku, int quantidade);
}
