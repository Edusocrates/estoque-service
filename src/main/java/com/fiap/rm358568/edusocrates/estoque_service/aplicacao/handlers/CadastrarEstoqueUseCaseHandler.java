package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;

import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.CadastrarEstoqueUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CadastrarEstoqueUseCaseHandler implements CadastrarEstoqueUseCase {

    private EstoqueGateway estoqueGateway;

    @Override
    public Estoque cadastrar(CriarEstoqueRequest estoqueRequest) {
        log.info("Cadastrando estoque com SKU: {}", estoqueRequest.sku());
        Estoque estoque = Estoque.builder()
                .sku(estoqueRequest.sku())
                .quantidadeDisponivel(estoqueRequest.quantidadeDisponivel())
                .build();

        Estoque estoqueSalvo = estoqueGateway.salvar(estoque);
        log.info("Estoque cadastrado com sucesso: {}", estoqueSalvo);
        return estoqueSalvo;
    }
}
