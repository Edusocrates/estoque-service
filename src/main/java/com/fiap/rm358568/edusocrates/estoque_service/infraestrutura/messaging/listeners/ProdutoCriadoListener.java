package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.listeners;

import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.CadastrarEstoqueUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.config.RabbitMQConfig;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.events.ProdutoCriadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoCriadoListener {

    private final CadastrarEstoqueUseCase cadastrarEstoqueUseCase;

    @RabbitListener(queues = RabbitMQConfig.PRODUTO_CRIADO_QUEUE)
    public void onProdutoCriado(ProdutoCriadoEvent event) {
        CriarEstoqueRequest estoque = new CriarEstoqueRequest(event.getSku(), 0);
        cadastrarEstoqueUseCase.cadastrar(estoque);
    }
}