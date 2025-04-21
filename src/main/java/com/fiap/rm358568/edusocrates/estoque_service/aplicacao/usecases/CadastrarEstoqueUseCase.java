package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases;

import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;

public interface CadastrarEstoqueUseCase {

    Estoque cadastrar(CriarEstoqueRequest estoqueRequest);
}
