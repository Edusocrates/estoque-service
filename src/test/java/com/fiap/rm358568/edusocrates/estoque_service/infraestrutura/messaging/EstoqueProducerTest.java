package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging;


import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.config.RabbitMQConfig;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.events.EstoqueAtualizadoEvent;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.producers.EstoqueProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EstoqueProducer estoqueProducer;

    @Test
    void enviarAtualizacaoEstoque_deveEnviarEventoCorretamente() {
        // Arrange
        Estoque estoque = Estoque.builder()
                .sku("SKU123")
                .quantidadeDisponivel(10)
                .build();

        EstoqueAtualizadoEvent expectedEvent = EstoqueAtualizadoEvent.builder()
                .sku(estoque.getSku())
                .quantidadeDisponivel(estoque.getQuantidadeDisponivel())
                .build();

        // Act
        estoqueProducer.enviarAtualizacaoEstoque(estoque);

        // Assert
        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.ESTOQUE_ATUALIZADO_EXCHANGE,
                RabbitMQConfig.ESTOQUE_ATUALIZADO_ROUTING_KEY,
                expectedEvent
        );
    }
}
