package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.producers;

import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.config.RabbitMQConfig;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.events.EstoqueAtualizadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EstoqueProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarAtualizacaoEstoque(Estoque estoque) {
        log.info("Enviando evento de atualização de estoque para o SKU: {}", estoque.getSku());
        EstoqueAtualizadoEvent event = EstoqueAtualizadoEvent.builder()
                .sku(estoque.getSku())
                .quantidadeDisponivel(estoque.getQuantidadeDisponivel())
                .build();

        log.info("Evento de atualização de estoque criado: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ESTOQUE_ATUALIZADO_EXCHANGE,
                RabbitMQConfig.ESTOQUE_ATUALIZADO_ROUTING_KEY,
                event
        );
        log.info("Evento de atualização de estoque enviado para a fila: {}", RabbitMQConfig.ESTOQUE_ATUALIZADO_EXCHANGE);
    }
}
