package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.producers;

import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.config.RabbitMQConfig;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.events.EstoqueAtualizadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstoqueProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarAtualizacaoEstoque(Estoque estoque) {
        EstoqueAtualizadoEvent event = EstoqueAtualizadoEvent.builder()
                .sku(estoque.getSku())
                .quantidadeDisponivel(estoque.getQuantidadeDisponivel())
                .build();

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ESTOQUE_ATUALIZADO_EXCHANGE,
                RabbitMQConfig.ESTOQUE_ATUALIZADO_ROUTING_KEY,
                event
        );
    }
}
